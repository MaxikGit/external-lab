package com.epam.esm.authorizationserver.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.authorizationserver")
public class AuthServerProperties {

    private String issuerUrl;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
