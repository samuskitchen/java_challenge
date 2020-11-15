package com.wolox.challenge.services.external;

import com.wolox.challenge.exceptions.ResourceNotFoundException;
import com.wolox.challenge.models.dtos.PhotoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@PropertySource("classpath:external.properties")
@Service
public class PhotoExternalService {

    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${endpoint.photos.url}")
    private String photosUrl;

    @Value("${endpoint.photos.album.url}")
    private String photosAlbumUrl;

    private final AlbumExternalService albumExternalService;

    @Autowired
    public PhotoExternalService(AlbumExternalService albumExternalService) {
        this.albumExternalService = albumExternalService;
    }

    public List<PhotoDTO> getAllPhotos() {
        ResponseEntity<List<PhotoDTO>> response;

        try {
            response = restTemplate.exchange(
                    photosUrl, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<PhotoDTO>>() {
                    }
            );
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(PhotoExternalService.class.toString(), "All Photos", "");
        }

        return response.getBody();
    }

    public PhotoDTO getPhotoById(Long photoId) {
        ResponseEntity<PhotoDTO> response;

        try {
            response = restTemplate.exchange(
                    photosUrl + "/" +
                            photoId.toString(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<PhotoDTO>() {
                    }
            );
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException(PhotoExternalService.class.toString(), "Photo Id", photoId);
        }

        return response.getBody();
    }

    public List<PhotoDTO> getPhotosByUserId(Long userId) {
        List<PhotoDTO> photos = new ArrayList<>();

        try {
            albumExternalService.getAlbumsByUserId(userId).forEach(albumDTO -> {
                photos.addAll(Objects.requireNonNull(restTemplate.exchange(
                        (photosAlbumUrl + albumDTO.getId()), HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<PhotoDTO>>() {
                        }
                ).getBody()));
            });
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(PhotoExternalService.class.toString(), "All Photos By User Id", "");
        }

        return photos;
    }
}