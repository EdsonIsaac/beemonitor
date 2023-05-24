package io.github.edsonisaac.beemonitor.configurations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * RSAKeyProperties is a component class that represents the RSA key properties.
 * It provides the getter methods for the public and private keys.
 *
 * @author Edson Isaac
 */
@Getter
@Component
public class RSAKeyProperties {

    @Value("${app.security.rsa.public-key}")
    private RSAPublicKey publicKey;

    @Value("${app.security.rsa.private-key}")
    private RSAPrivateKey privateKey;
}