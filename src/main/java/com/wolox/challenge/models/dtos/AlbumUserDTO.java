package com.wolox.challenge.models.dtos;

import com.wolox.challenge.models.enums.AccessType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumUserDTO {

    private Long id;

    private AlbumDTO album;

    private UserDTO user;

    private AccessType accessType;
}
