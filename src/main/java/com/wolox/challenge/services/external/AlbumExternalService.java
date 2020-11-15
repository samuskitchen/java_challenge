package com.wolox.challenge.services.external;

import com.wolox.challenge.exceptions.ResourceNotFoundException;
import com.wolox.challenge.models.Album;
import com.wolox.challenge.models.dtos.AlbumDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@PropertySource("classpath:external.properties")
@Service
public class AlbumExternalService {

    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${endpoint.albums.url}")
    private String albumsUrl;

    @Value("${endpoint.albums.user.url}")
    private String albumUserUrl;

    @Async("asyncExecutor")
    public CompletableFuture<Album> getAlbumAsyncById(Long albumId) {
        ResponseEntity<Album> response;

        try {
            response = restTemplate.exchange(
                    albumsUrl + "/" +
                            albumId.toString(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<Album>() {
                    }
            );
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException(AlbumExternalService.class.toString(), "Album Id", albumId);
        }

        return CompletableFuture.completedFuture(response.getBody());
    }

    public List<AlbumDTO> getAllAlbums() {
        ResponseEntity<List<AlbumDTO>> response;

        try {
            response = restTemplate.exchange(
                    albumsUrl, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<AlbumDTO>>() {
                    }
            );
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException(AlbumExternalService.class.toString(), "All Albums", "");
        }

        return response.getBody();
    }

    public AlbumDTO getAlbumById(Long albumId) {
        ResponseEntity<AlbumDTO> response;

        try {
            response = restTemplate.exchange(
                    albumsUrl + "/" +
                            albumId.toString(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<AlbumDTO>() {
                    }
            );
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException(AlbumExternalService.class.toString(), "Album Id", albumId);
        }

        return response.getBody();
    }

    public List<AlbumDTO> getAlbumsByUserId(Long userId) {
        ResponseEntity<List<AlbumDTO>> response;

        try {
            response = restTemplate.exchange(
                    albumUserUrl +
                            userId.toString(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<AlbumDTO>>() {
                    }
            );
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException(AlbumExternalService.class.toString(), "User Id", userId);
        }

        return response.getBody();
    }
}
