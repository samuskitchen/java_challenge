package com.wolox.challenge.controllers;

import com.wolox.challenge.models.dtos.AlbumDTO;
import com.wolox.challenge.services.external.AlbumExternalService;
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
@RequestMapping("/albums")
@Api(tags = "Albums of External Api")
public class AlbumController {

    private final AlbumExternalService albumExternalService;

    @Autowired
    public AlbumController(AlbumExternalService albumExternalService) {
        this.albumExternalService = albumExternalService;
    }

    @GetMapping
    @ApiOperation(value = "Get All Albums", response = AlbumDTO[].class)
    public List<AlbumDTO> getAllAlbums() {
        return albumExternalService.getAllAlbums();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Album By Id", response = AlbumDTO.class)
    public AlbumDTO getAlbumById(@PathVariable(value = "id") Long albumId) {
        return albumExternalService.getAlbumById(albumId);
    }

    @GetMapping(params = "userId")
    @ApiOperation(value = "Get All Albums By Id", response = AlbumDTO.class)
    public List<AlbumDTO> getAlbumsByUserId(@RequestParam(value = "userId") Long userId) {
        return albumExternalService.getAlbumsByUserId(userId);
    }
}