package com.wolox.challenge.controllers;

import com.wolox.challenge.exceptions.AlbumUserException;
import com.wolox.challenge.exceptions.ResourceNotFoundException;
import com.wolox.challenge.models.AlbumUser;
import com.wolox.challenge.models.User;
import com.wolox.challenge.models.dtos.AlbumUserDTO;
import com.wolox.challenge.models.dtos.UserDTO;
import com.wolox.challenge.services.AlbumService;
import com.wolox.challenge.services.external.AlbumExternalService;
import com.wolox.challenge.services.external.UserExternalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/shared")
@Api(tags = "Shared Albums")
public class AlbumUserController {

    private final AlbumService albumService;
    private UserExternalService userExternalService;
    private AlbumExternalService albumExternalService;

    @Autowired
    public AlbumUserController(AlbumService albumService, UserExternalService userExternalService, AlbumExternalService albumExternalService) {
        this.albumService = albumService;
        this.userExternalService = userExternalService;
        this.albumExternalService = albumExternalService;
    }

    @PostMapping("/album")
    @ApiOperation(value = "Associate an album to a user with permission to grant an access", response = AlbumUser.class)
    public CompletableFuture<AlbumUserDTO> shareAlbumWithUser(@PathParam("albumId") Long albumId,
                                                              @PathParam("userId") Long userId,
                                                              @PathParam("accessTypeId") Long accessTypeId) {

        return userExternalService.getUserAsyncById(userId)
                .thenComposeAsync(user -> albumExternalService.getAlbumAsyncById(albumId)
                        .thenComposeAsync(album -> albumService.sharedAlbumWithUser(album, user, accessTypeId))
                        .thenApply(albumUser -> albumUser
                                .orElseThrow(() -> new AlbumUserException("You do not have permission to save the record"))));
    }

    @PutMapping("/album")
    @ApiOperation(value = "Update the access permission for a user to the album", response = AlbumUser.class)
    public ResponseEntity<AlbumUserDTO> updateAccessToAlbum(@PathParam("albumId") Long albumId,
                                                         @PathParam("userId") Long userId,
                                                         @PathParam("accessTypeId") Long accessTypeId) {
        return ResponseEntity.ok(albumService.updateAccessToAlbum(albumId, userId, accessTypeId)
                .orElseThrow(() -> new AlbumUserException("Problems updating album access")));
    }

    @GetMapping("/all/users/album/access-type")
    @ApiOperation(value = "Get all users with the access permission associated with an album", response = AlbumUser[].class)
    public ResponseEntity<List<UserDTO>> getAllUsersByAlbumAndAccessType(@PathParam("albumId") Long albumId,
                                                                         @PathParam("accessTypeId") Long accessTypeId) {
        return ResponseEntity.ok(albumService.getAllUsersByAlbumAndAccessType(albumId, accessTypeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        AlbumUserController.class.toString(),
                        "albumId, accessTypeId",
                        (albumId + ", " + accessTypeId))));
    }

}
