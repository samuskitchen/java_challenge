package com.wolox.challenge.controllers;

import com.wolox.challenge.models.dtos.CommentDTO;
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
class CommentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllComments() throws MalformedURLException {
        ResponseEntity<List<CommentDTO>> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/api/challenge/comments").toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CommentDTO>>() {
                });

        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody(), not(IsEmptyCollection.empty()));
    }

    @Test
    void getCommentById() throws MalformedURLException {
        ResponseEntity<CommentDTO> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/api/challenge/comments/1").toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<CommentDTO>() {
                });

        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void getAllCommentsByName() throws MalformedURLException {
        ResponseEntity<List<CommentDTO>> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/api/challenge/comments/all/name?name=alias odio sit").toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CommentDTO>>() {
                });

        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody(), not(IsEmptyCollection.empty()));
    }

    @Test
    void getAllCommentsByUserId() throws MalformedURLException {
        ResponseEntity<List<CommentDTO>> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/api/challenge/comments/all/user?userId=1").toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CommentDTO>>() {
                });

        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody(), not(IsEmptyCollection.empty()));
    }
}