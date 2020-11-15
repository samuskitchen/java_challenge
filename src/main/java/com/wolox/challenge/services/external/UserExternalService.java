package com.wolox.challenge.services.external;

import com.wolox.challenge.exceptions.ResourceNotFoundException;
import com.wolox.challenge.models.User;
import com.wolox.challenge.models.dtos.UserDTO;
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
public class UserExternalService {

    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${endpoint.users.url}")
    private String usersUrl;

    @Async("asyncExecutor")
    public CompletableFuture<User> getUserAsyncById(Long userId) {
        ResponseEntity<User> response;

        try {
            response = restTemplate.exchange(
                    usersUrl + "/" +
                            userId.toString(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<User>() {
                    }
            );
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException(UserExternalService.class.toString(), "User Id", userId);
        }

        return CompletableFuture.completedFuture(response.getBody());
    }

    public List<UserDTO> getAllUsers() {
        ResponseEntity<List<UserDTO>> response;

        try {
            response = restTemplate.exchange(
                    usersUrl, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<UserDTO>>() {
                    }
            );
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(UserExternalService.class.toString(), "All Users", "");
        }

        return response.getBody();
    }

    public UserDTO getUserById(Long userId) {
        ResponseEntity<UserDTO> response;

        try {
            response = restTemplate.exchange(
                    usersUrl + "/" +
                            userId.toString(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<UserDTO>() {
                    }
            );
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException(UserExternalService.class.toString(), "User Id", userId);
        }

        return response.getBody();
    }
}
