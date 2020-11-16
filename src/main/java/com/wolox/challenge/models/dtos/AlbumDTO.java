package com.wolox.challenge.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlbumDTO {

    private Long id;

    private Long userId;

    private String title;
}
