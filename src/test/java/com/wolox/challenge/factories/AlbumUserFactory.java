package com.wolox.challenge.factories;

import com.wolox.challenge.models.Album;
import com.wolox.challenge.models.AlbumUser;
import com.wolox.challenge.models.User;
import com.wolox.challenge.models.dtos.AlbumUserDTO;
import com.wolox.challenge.models.enums.AccessType;
import org.fluttercode.datafactory.impl.DataFactory;

public class AlbumUserFactory {

    private static final DataFactory dataFactory = new DataFactory();

    private Long id;

    private final Album album;

    private final User user;

    private AccessType accessType;

    public AlbumUserFactory() {
        this.id = (long) dataFactory.getNumberBetween(1, 10);
        this.album = new AlbumFactory().newInstance();
        this.user = new UserFactory().newInstance();
        this.accessType = AccessType.READ_WRITE;
    }

    public AlbumUser newInstance() {
        return AlbumUser.builder()
                .id(this.id)
                .user(this.user)
                .album(this.album)
                .accessType(this.accessType)
                .build();
    }

    public AlbumUserDTO newInstanceDTO() {
        return AlbumUserDTO.builder()
                .id(this.id)
                .user(new UserFactory().newInstanceDTO())
                .album(new AlbumFactory().newInstanceDTO())
                .accessType(this.accessType)
                .build();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }
}
