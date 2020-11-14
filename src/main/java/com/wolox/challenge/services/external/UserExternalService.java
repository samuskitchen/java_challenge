package com.wolox.challenge.services.external;

import com.wolox.challenge.exceptions.ResourceNotFoundException;
import com.wolox.challenge.models.User;
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
public class UserExternalService {

    @Value("${endpoint.users.url}")
    private String usersUrl;

    private static final RestTemplate restTemplate = new RestTemplate();

    @Async("asyncExecutor")
    public CompletableFuture<User> getUserById(Long userId) {

        ResponseEntity<User> response = restTemplate.exchange(
                usersUrl + "/" +
                        userId.toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<User>() {
                }
        );

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ResourceNotFoundException(UserExternalService.class.toString(), "id", userId);
        }

        return CompletableFuture.completedFuture(response.getBody());
    }

}
