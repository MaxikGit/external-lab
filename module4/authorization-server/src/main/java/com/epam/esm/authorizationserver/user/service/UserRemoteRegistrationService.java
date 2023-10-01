package com.epam.esm.authorizationserver.user.service;

import com.epam.esm.authorizationserver.common.properties.ResourceServerProperties;
import com.epam.esm.authorizationserver.user.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserRemoteRegistrationService {

    private final ResourceServerProperties properties;
    private final RestTemplate restTemplate;

    public void save(SignUpDto userDto) {
        HttpEntity<SignUpDto> requestEntity = new HttpEntity<>(userDto, getHeaders());
        restTemplate.postForEntity(properties.getUri(), requestEntity, Void.class);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(properties.getUsername(), properties.getPassword());
        return headers;
    }
}
