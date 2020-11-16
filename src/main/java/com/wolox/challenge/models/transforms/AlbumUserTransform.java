package com.wolox.challenge.models.transforms;

import com.wolox.challenge.models.AlbumUser;
import com.wolox.challenge.models.dtos.AlbumUserDTO;

public class AlbumUserTransform {

    private static final UserTransform userTransform = new UserTransform();
    private static final AlbumTransform albumTransform = new AlbumTransform();

    public AlbumUserDTO AlbumUserToAlbumUserDTO(AlbumUser albumUser) {
        AlbumUserDTO albumUserDTOResponse = null;

        if (null != albumUser) {
            albumUserDTOResponse = AlbumUserDTO.builder()
                    .id(albumUser.getId())
                    .accessType(albumUser.getAccessType())
                    .album(albumTransform.AlbumToAlbumDTO(albumUser.getAlbum()))
                    .user(userTransform.userToUserDTO(albumUser.getUser()))
                    .build();
        }

        return albumUserDTOResponse;
    }
}
