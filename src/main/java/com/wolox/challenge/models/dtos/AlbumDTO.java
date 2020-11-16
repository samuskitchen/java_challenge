package com.wolox.challenge.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDTO {

    private Long id;

    private Long userId;

    private String title;
}

