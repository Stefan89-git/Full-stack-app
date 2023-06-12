package com.stefan.integrationtests.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CustomWebTestClient {
    private final WebTestClient webTestClient;

    public <E> E doGet(String url, Class<E> clazz) {
        return webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(clazz)
                .returnResult()
                .getResponseBody();
    }

    public <E> List<E> doGetList(String url, Class<E> clazz) {
        return webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(clazz)
                .returnResult()
                .getResponseBody();
    }

    public <E> E doPost(String url, E dto, Class<E> clazz) {
        return webTestClient.post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), clazz)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(clazz)
                .returnResult()
                .getResponseBody();
    }

    public <E> E doPut(String url, E dto, Class<E> clazz) {
        return webTestClient.put()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), clazz)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(clazz)
                .returnResult()
                .getResponseBody();
    }

    public void doDelete(String url) {
        webTestClient.delete()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
