package com.monitoria.habibs.infrastructure.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

@Configuration
public class JwtConfig {

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    public JwtConfig(
            @Value("${security.jwt.public-key-location}") Resource publicKeyResource,
            @Value("${security.jwt.private-key-location}") Resource privateKeyResource) {
        this.publicKey = loadPublicKey(publicKeyResource);
        this.privateKey = loadPrivateKey(privateKeyResource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .build();

        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(rsaKey)));
    }

    private RSAPublicKey loadPublicKey(Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            return (RSAPublicKey) RsaKeyConverters.x509().convert(inputStream);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load RSA public key", exception);
        }
    }

    private RSAPrivateKey loadPrivateKey(Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            return (RSAPrivateKey) RsaKeyConverters.pkcs8().convert(inputStream);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load RSA private key", exception);
        }
    }
}