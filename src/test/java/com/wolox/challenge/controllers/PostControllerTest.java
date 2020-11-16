package com.wolox.challenge.controllers;

import com.wolox.challenge.models.dtos.CommentDTO;
import com.wolox.challenge.models.dtos.PostDTO;
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
class PostControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllPosts() throws MalformedURLException {
        ResponseEntity<List<PostDTO>> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/api/challenge/posts").toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<PostDTO>>() {
                });

        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody(), not(IsEmptyCollection.empty()));
    }

    @Test
    void getPostById() throws MalformedURLException {
        ResponseEntity<PostDTO> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/api/challenge/posts/1").toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<PostDTO>() {
                });

        Assertions.assertNotNull(response.getBody());
    }
}