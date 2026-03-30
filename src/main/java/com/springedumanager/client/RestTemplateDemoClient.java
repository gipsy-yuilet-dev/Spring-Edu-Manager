package com.springedumanager.client;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.springedumanager.dto.CursoResponse;

@Component
@ConditionalOnProperty(name = "app.rest.client.enabled", havingValue = "true")
/**
 * Cliente de ejemplo que consume la API REST local usando RestTemplate.
 */
public class RestTemplateDemoClient implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateDemoClient.class);

    private final RestTemplate restTemplate;

    @Value("${app.security.admin.username}")
    private String username;

    @Value("${app.security.admin.password}")
    private String password;

    @Value("${server.port:8080}")
    private String serverPort;

    public RestTemplateDemoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(String... args) {
        String endpoint = "http://localhost:" + serverPort + "/api/cursos";
        String authHeader = "Basic " + Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));

        RequestEntity<Void> request = RequestEntity
                .method(HttpMethod.GET, URI.create(endpoint))
                .header("Authorization", authHeader)
                .build();

        ResponseEntity<CursoResponse[]> response = restTemplate.exchange(request, CursoResponse[].class);
        CursoResponse[] cursos = response.getBody();
        int total = cursos == null ? 0 : cursos.length;

        log.info("RestTemplate demo -> GET {} retornó {} cursos", endpoint, total);
    }
}
