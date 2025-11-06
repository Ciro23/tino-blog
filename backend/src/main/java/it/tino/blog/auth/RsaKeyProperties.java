package it.tino.blog.auth;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rsa")
record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {

}
