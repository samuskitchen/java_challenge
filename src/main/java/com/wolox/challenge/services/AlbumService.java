package com.wolox.challenge.services;

import com.wolox.challenge.exceptions.ResourceNotFoundException;
import com.wolox.challenge.models.Album;
import com.wolox.challenge.models.AlbumUser;
import com.wolox.challenge.models.User;
import com.wolox.challenge.models.enums.AccessType;
import com.wolox.challenge.repositories.AlbumRepository;
import com.wolox.challenge.repositories.AlbumUserRepository;
import com.wolox.challenge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class AlbumService {

    private final AlbumUserRepository albumUserRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumService(AlbumUserRepository albumUserRepository, UserRepository userRepository, AlbumRepository albumRepository) {
        this.albumUserRepository = albumUserRepository;
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
    }

    @Async("asyncExecutor")
    public CompletableFuture<Optional<AlbumUser>> sharedAlbumWithUser(Album album, User user, Long accessTypeId) {
        AccessType accessType = AccessType.getByIdAccess(accessTypeId);

        if (accessType.equals(AccessType.UNKNOWN)) {
            throw new ResourceNotFoundException(AccessType.class.toString(), "id", accessTypeId);
        }

        // The permission will be saved if the record does not exist in AlbumUser
        // and it is not shared with the owner
        if (!this.existsAlbumUser(album.getEndpointId(), user.getEndpointId())
                && !album.getOwnerEndpointId().equals(user.getEndpointId())) {

            user = userRepository.findByEndpointId(user.getEndpointId()).orElse(userRepository.save(user));

            album = albumRepository.findByEndpointId(album.getEndpointId()).orElse(albumRepository.save(album));

            AlbumUser albumUser = AlbumUser.builder().user(user).album(album).accessType(accessType).build();

            return CompletableFuture.completedFuture(Optional.of(albumUserRepository.save(albumUser)));
        }

        return CompletableFuture.completedFuture(Optional.empty());
    }

    public Optional<AlbumUser> updateAccessToAlbum(Long albumId, Long userId, Long accessTypeId) {
        AccessType accessType = AccessType.getByIdAccess(accessTypeId);

        if (accessType.equals(AccessType.UNKNOWN)) {
            throw new ResourceNotFoundException(AccessType.class.toString(), "id", accessTypeId);
        }

        AlbumUser albumUser = albumUserRepository.findByAlbum_IdAndUser_Id(albumId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        AlbumUser.class.toString(), "id", (albumId + ", " + userId)));


        AlbumUser albumUserSave = AlbumUser.builder()
                .album(albumUser.getAlbum())
                .user(albumUser.getUser())
                .accessType(accessType)
                .build();

        return Optional.of(albumUserRepository.save(albumUserSave));
    }

    public Optional<List<User>> getAllUsersByAlbumAndAccessType(Long albumId, Long accessTypeId) {
        AccessType accessType = AccessType.getByIdAccess(accessTypeId);

        if (accessType.equals(AccessType.UNKNOWN)) {
            throw new ResourceNotFoundException(AccessType.class.toString(), "id", accessTypeId);
        }

        return Optional.of(userRepository.findByAlbumIdAndAccessType(albumId, accessType));
    }

    private Boolean existsAlbumUser(Long albumId, Long userId) {
        return albumUserRepository.existsAlbumUserByAlbum_IdAndUser_Id(albumId, userId);
    }
}