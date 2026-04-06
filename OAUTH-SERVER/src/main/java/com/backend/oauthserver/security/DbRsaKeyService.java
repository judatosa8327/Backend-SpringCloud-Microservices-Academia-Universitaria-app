package com.backend.oauthserver.security;

import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Component
public class DbRsaKeyService {

    private final JdbcTemplate jdbcTemplate;

    public DbRsaKeyService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public RSAKey getOrCreateRsaKey() {
        List<RSAKey> keys = jdbcTemplate.query(
                "SELECT key_id, public_key, private_key FROM oauth_rsa_keys ORDER BY created_at DESC LIMIT 1",
                (rs, rowNum) -> {
                    String keyId = rs.getString("key_id");
                    String publicKeyBase64 = rs.getString("public_key");
                    String privateKeyBase64 = rs.getString("private_key");
                    return mapToRsaKey(keyId, publicKeyBase64, privateKeyBase64);
                }
        );

        if (!keys.isEmpty()) {
            return keys.get(0);
        }

        return createAndPersistRsaKey();
    }

    private RSAKey createAndPersistRsaKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            String keyId = UUID.randomUUID().toString();

            String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            jdbcTemplate.update(
                    "INSERT INTO oauth_rsa_keys (key_id, public_key, private_key) VALUES (?, ?, ?)",
                    keyId,
                    publicKeyBase64,
                    privateKeyBase64
            );

            return new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyID(keyId)
                    .build();

        } catch (Exception ex) {
            throw new IllegalStateException("Error creating or persisting RSA key", ex);
        }
    }

    private RSAKey mapToRsaKey(String keyId, String publicKeyBase64, String privateKeyBase64) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(
                    new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBase64))
            );
            RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBase64))
            );

            return new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyID(keyId)
                    .build();
        } catch (Exception ex) {
            throw new IllegalStateException("Error loading RSA key from database", ex);
        }
    }
}
