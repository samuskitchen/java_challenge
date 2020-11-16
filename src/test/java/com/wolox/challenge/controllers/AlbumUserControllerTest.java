package com.wolox.challenge.controllers;

import com.wolox.challenge.models.dtos.AlbumUserDTO;
import com.wolox.challenge.models.dtos.UserDTO;
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
class AlbumUserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shareAlbumWithUser() throws MalformedURLException {
        ResponseEntity<AlbumUserDTO> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/api/challenge/shared/album?albumId=2&userId=3&accessTypeId=3").toString(),
                HttpMethod.POST, null, new ParameterizedTypeReference<AlbumUserDTO>() {
                });

        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void updateAccessToAlbum() throws MalformedURLException {
        ResponseEntity<AlbumUserDTO> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/api/challenge/shared/album?albumId=2&userId=3&accessTypeId=2").toString(),
                HttpMethod.PUT, null, new ParameterizedTypeReference<AlbumUserDTO>() {
                });

        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void getAllUsersByAlbumAndAccessType() throws MalformedURLException {
        ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/api/challenge/shared/all/users?albumId=1&accessTypeId=2").toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<UserDTO>>() {
                });

        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody(), not(IsEmptyCollection.empty()));
    }
}