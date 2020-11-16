package com.wolox.challenge.controllers;

import com.wolox.challenge.models.dtos.PhotoDTO;
import com.wolox.challenge.services.external.PhotoExternalService;
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
@RequestMapping("/photos")
@Api(tags = "Photos of External Api")
public class PhotoController {

    private final PhotoExternalService photoExternalService;

    @Autowired
    public PhotoController(PhotoExternalService photoExternalService) {
        this.photoExternalService = photoExternalService;
    }

    @GetMapping
    @ApiOperation(value = "Get All Photos", response = PhotoDTO[].class)
    public List<PhotoDTO> getAllPhotos() {
        return photoExternalService.getAllPhotos();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Photo By Id", response = PhotoDTO.class)
    public PhotoDTO getPhotoById(@PathVariable(value = "id") Long photoId) {
        return photoExternalService.getPhotoById(photoId);
    }

    @GetMapping("/all/user")
    @ApiOperation(value = "Get All Photos By User Id", response = PhotoDTO[].class)
    public List<PhotoDTO> getPhotosByUserId(@RequestParam("userId") Long userId) {
        return photoExternalService.getPhotosByUserId(userId);
    }
}
