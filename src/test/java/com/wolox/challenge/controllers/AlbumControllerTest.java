package com.wolox.challenge.controllers;

import com.wolox.challenge.models.dtos.AlbumDTO;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlbumControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllAlbums() throws MalformedURLException {
        ResponseEntity<List<AlbumDTO>> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/api/challenge/albums").toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<AlbumDTO>>() {
                });

        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody(), not(IsEmptyCollection.empty()));
    }

    @Test
    void getAlbumById() throws MalformedURLException {
        ResponseEntity<AlbumDTO> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/api/challenge/albums/1").toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<AlbumDTO>() {
                });

        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void getAlbumsByUserId() throws MalformedURLException {
        ResponseEntity<List<AlbumDTO>> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/api/challenge/albums/all/user?userId=1").toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<AlbumDTO>>() {
                });

        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody(), not(IsEmptyCollection.empty()));
    }
}