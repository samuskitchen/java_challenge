package com.wolox.challenge.models.dtos;

import lombok.Data;

@Data
public class PhotoDTO {

    private Long id;

    private Long albumId;

    private String title;

    private String url;

    private String thumbnailUrl;
}