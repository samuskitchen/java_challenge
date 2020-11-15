package com.wolox.challenge.services.external;

import com.wolox.challenge.exceptions.ResourceNotFoundException;
import com.wolox.challenge.models.dtos.CommentDTO;
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
public class CommentExternalService {

    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${endpoint.comments.url}")
    private String commentsUrl;

    @Value("${endpoint.comments.name.url}")
    private String commentsNameUrl;

    @Value("${endpoint.comments.post.url}")
    private String commentsPostUrl;

    private final PostExternalService postExternalService;

    @Autowired
    public CommentExternalService(PostExternalService postExternalService) {
        this.postExternalService = postExternalService;
    }

    public List<CommentDTO> getAllComments() {
        ResponseEntity<List<CommentDTO>> response;

        try {
            response = restTemplate.exchange(
                    commentsUrl, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<CommentDTO>>() {
                    }
            );
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(CommentExternalService.class.toString(), "All Comments", "");
        }

        return response.getBody();
    }


    public CommentDTO getCommentById(Long commentId) {
        ResponseEntity<CommentDTO> response;

        try {
            response = restTemplate.exchange(
                    commentsUrl + "/" +
                            commentId.toString(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<CommentDTO>() {
                    }
            );
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException(CommentExternalService.class.toString(), "Comment Id", commentId);
        }

        return response.getBody();
    }

    public List<CommentDTO> getAllCommentsByName(String name) {
        ResponseEntity<List<CommentDTO>> responseComments;

        try {
            responseComments = restTemplate.exchange(
                    commentsNameUrl + name, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<CommentDTO>>() {
                    }
            );
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException(CommentExternalService.class.toString(), "Comments By Name", name);
        }

        return responseComments.getBody();
    }

    public List<CommentDTO> getAllCommentsByUserId(Long userId) {
        List<CommentDTO> comments = new ArrayList<>();

        try {
            postExternalService.getPostByUserId(userId).forEach(postDTO -> {
                comments.addAll(Objects.requireNonNull(restTemplate.exchange(
                        (commentsPostUrl + postDTO.getId()), HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<CommentDTO>>() {
                        }
                ).getBody()));
            });
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException(CommentExternalService.class.toString(), "Comments By User Id", userId);
        }

        return comments;
    }
}
