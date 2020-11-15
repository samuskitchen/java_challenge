package com.wolox.challenge.services.external;

import com.wolox.challenge.exceptions.ResourceNotFoundException;
import com.wolox.challenge.models.dtos.PostDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@PropertySource("classpath:external.properties")
@Service
public class PostExternalService {

    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${endpoint.posts.url}")
    private String postsUrl;

    @Value("${endpoint.posts.user.url}")
    private String postsUserUrl;

    public List<PostDTO> getAllPosts() {
        ResponseEntity<List<PostDTO>> response;

        try {
            response = restTemplate.exchange(
                    postsUrl, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<PostDTO>>() {
                    }
            );
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(PostExternalService.class.toString(), "All Post", "");
        }

        return response.getBody();
    }

    public PostDTO getPostById(Long postId) {
        ResponseEntity<PostDTO> response;

        try {
            response = restTemplate.exchange(
                    postsUrl + "/" +
                            postId.toString(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<PostDTO>() {
                    }
            );
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException(PostExternalService.class.toString(), "Post Id", postId);
        }

        return response.getBody();
    }

    public List<PostDTO> getPostByUserId(Long userId) {
        ResponseEntity<List<PostDTO>> response;

        try {
            response = restTemplate.exchange(
                    postsUserUrl + userId.toString(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<PostDTO>>() {
                    }
            );
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException(PostExternalService.class.toString(), "Post By User Id", userId);
        }

        return response.getBody();
    }

}
