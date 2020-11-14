package com.wolox.challenge.services.external;

import com.wolox.challenge.exceptions.ResourceNotFoundException;
import com.wolox.challenge.models.Album;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@PropertySource("classpath:external.properties")
@Service
public class AlbumExternalService {

    @Value("${endpoint.albums.url}")
    private String albumsUrl;

    private static final RestTemplate restTemplate = new RestTemplate();

    @Async("asyncExecutor")
    public CompletableFuture<Album> getAlbumById(Long albumId) {

        ResponseEntity<Album> response = restTemplate.exchange(
                albumsUrl + "/" +
                        albumId.toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<Album>() {
                }
        );

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ResourceNotFoundException(AlbumExternalService.class.toString(), "id", albumId);
        }

        return CompletableFuture.completedFuture(response.getBody());
    }

}
