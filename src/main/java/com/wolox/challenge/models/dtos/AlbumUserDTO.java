package com.wolox.challenge.models.dtos;

import com.wolox.challenge.models.enums.AccessType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlbumUserDTO {

    private Long id;

    private AlbumDTO album;

    private UserDTO user;

    private AccessType accessType;
}
