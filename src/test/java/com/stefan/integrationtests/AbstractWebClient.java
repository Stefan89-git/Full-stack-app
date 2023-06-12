package com.stefan.integrationtests;

import com.stefan.integrationtests.util.CustomWebTestClient;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractWebClient<E> {
    protected static final String BASE_URL = "http://localhost:9000/api/v1/";

    private final CustomWebTestClient webTestClient;
    private final String path;
    private final Class<E> clazz;

    public List<E> getAll() {
        String url = BASE_URL + path;
        return webTestClient.doGetList(url, clazz);
    }

    public E getOne(int id) {
        String url = "%s/%d".formatted(BASE_URL + path, id);
        return webTestClient.doGet(url, clazz);
    }

    public E create(E data) {
        String url = BASE_URL + path;
        return webTestClient.doPost(url, data, clazz);
    }

    public E update(int id, E data) {
        String url = "%s/%d".formatted(BASE_URL + path, id);
        return webTestClient.doPut(url, data, clazz);
    }

    public void delete(int id) {
        String url = "%s/%d".formatted(BASE_URL + path, id);
        webTestClient.doDelete(url);
    }
}
