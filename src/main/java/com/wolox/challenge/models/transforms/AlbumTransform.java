package com.wolox.challenge.models.transforms;

import com.wolox.challenge.models.Album;
import com.wolox.challenge.models.dtos.AlbumDTO;

public class AlbumTransform {

    public AlbumDTO AlbumToAlbumDTO(Album album){
        AlbumDTO albumDTOResponse = null;

        if (null != album){
            albumDTOResponse = AlbumDTO.builder()
                    .id(album.getEndpointId())
                    .title(album.getTitle())
                    .userId(album.getOwnerEndpointId())
                    .build();
        }

        return albumDTOResponse;
    }
}
