package com.wolox.challenge.factories;

import com.wolox.challenge.models.Album;
import com.wolox.challenge.models.dtos.AlbumDTO;
import org.fluttercode.datafactory.impl.DataFactory;

public class AlbumFactory {

    private static final DataFactory dataFactory = new DataFactory();

    private Long id;

    private Long endpointId;

    private Long ownerEndpointId;

    private final String title;

    public AlbumFactory() {
        this.id = (long) dataFactory.getNumberBetween(1, 10);
        this.endpointId = (long) dataFactory.getNumberBetween(1, 10);
        this.ownerEndpointId = (long) dataFactory.getNumberBetween(1, 10);
        this.title = dataFactory.getRandomWord(1, 20);
    }

    public Album newInstance() {
        return Album.builder()
                .id(this.id)
                .endpointId(this.endpointId)
                .ownerEndpointId(this.ownerEndpointId)
                .title(this.title)
                .build();
    }

    public AlbumDTO newInstanceDTO() {
        return AlbumDTO.builder()
                .id(this.endpointId)
                .userId(this.ownerEndpointId)
                .title(this.title)
                .build();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEndpointId(Long endpointId) {
        this.endpointId = endpointId;
    }

    public void setOwnerEndpointId(Long ownerEndpointId) {
        this.ownerEndpointId = ownerEndpointId;
    }
}
