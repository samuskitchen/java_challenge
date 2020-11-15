package com.wolox.challenge.controllers;

import com.wolox.challenge.models.dtos.CommentDTO;
import com.wolox.challenge.services.external.CommentExternalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
@Api(tags = "Comments of External Api")
public class CommentController {

    private final CommentExternalService commentExternalService;

    @Autowired
    public CommentController(CommentExternalService commentExternalService) {
        this.commentExternalService = commentExternalService;
    }

    @GetMapping
    @ApiOperation(value = "Get All Comments", response = CommentDTO[].class)
    public List<CommentDTO> getAllComments() {
        return commentExternalService.getAllComments();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Comment By Id", response = CommentDTO.class)
    public CommentDTO getCommentById(@PathVariable(value = "id") Long commentId) {
        return commentExternalService.getCommentById(commentId);
    }

    @GetMapping("/name")
    @ApiOperation(value = "Get All Comments By Name", response = CommentDTO[].class)
    public List<CommentDTO> getAllCommentsByName(@RequestParam(value = "name") String name) {
        return commentExternalService.getAllCommentsByName(name);
    }

    @GetMapping("/user")
    @ApiOperation(value = "Get All Comments By User Id", response = CommentDTO[].class)
    public List<CommentDTO> getAllCommentsByUserId(@RequestParam(value = "userId") Long userId) {
        return commentExternalService.getAllCommentsByUserId(userId);
    }
}
