package fr.hb.mlang.projectrestapi.config.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtKeyManager {

  @Value("${app.jwt.keys.location}")
  private Path keyDirectory;

  private Algorithm algorithm;

  public Algorithm getAlgorithm() {
    return algorithm;
  }

  @PostConstruct
  private void initialize() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    Path publicFile = keyDirectory.resolve("public.key");
    Path privateFile = keyDirectory.resolve("private.key");
    KeyPair keyPair;

    if (Files.notExists(publicFile) || Files.notExists(privateFile)) {
      KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
      keyPair = generator.generateKeyPair();

      Files.write(publicFile, keyPair.getPublic().getEncoded());
      Files.write(privateFile, keyPair.getPrivate().getEncoded());
    } else {
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      keyPair = new KeyPair(
          keyFactory.generatePublic(new X509EncodedKeySpec(Files.readAllBytes(publicFile))),
          keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Files.readAllBytes(privateFile)))
      );
    }

    algorithm = Algorithm.RSA256(
        (RSAPublicKey) keyPair.getPublic(),
        (RSAPrivateKey) keyPair.getPrivate()
    );
  }
}
