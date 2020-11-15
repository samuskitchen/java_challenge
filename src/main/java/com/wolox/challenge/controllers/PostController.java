package com.wolox.challenge.controllers;

import com.wolox.challenge.models.dtos.PostDTO;
import com.wolox.challenge.models.dtos.UserDTO;
import com.wolox.challenge.services.external.PostExternalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
@Api(tags = "Posts of External Api")
public class PostController {

    private final PostExternalService postExternalService;

    @Autowired
    public PostController(PostExternalService postExternalService) {
        this.postExternalService = postExternalService;
    }

    @GetMapping
    @ApiOperation(value = "Get All Posts", response = PostDTO[].class)
    public List<PostDTO> getAllPosts() {
        return postExternalService.getAllPosts();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Post By Id", response = UserDTO.class)
    public PostDTO getPostById(@PathVariable(value = "id") Long postId) {
        return postExternalService.getPostById(postId);
    }
}
