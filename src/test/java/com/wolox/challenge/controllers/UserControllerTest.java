package com.wolox.challenge.controllers;

import com.wolox.challenge.models.User;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
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

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllUsers() throws MalformedURLException {
        ResponseEntity<List<User>> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/api/challenge/users").toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {
                });

        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody(), not(IsEmptyCollection.empty()));
    }

    @Test
    void getUserById() throws MalformedURLException {
        ResponseEntity<User> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/api/challenge/users/1").toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<User>() {
                });

        Assertions.assertNotNull(response.getBody());
    }
}