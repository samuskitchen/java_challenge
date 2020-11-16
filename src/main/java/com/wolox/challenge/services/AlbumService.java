package com.wolox.challenge.services;

import com.wolox.challenge.exceptions.ResourceNotFoundException;
import com.wolox.challenge.models.Album;
import com.wolox.challenge.models.AlbumUser;
import com.wolox.challenge.models.User;
import com.wolox.challenge.models.dtos.AlbumUserDTO;
import com.wolox.challenge.models.dtos.UserDTO;
import com.wolox.challenge.models.enums.AccessType;
import com.wolox.challenge.models.transforms.AlbumUserTransform;
import com.wolox.challenge.models.transforms.UserTransform;
import com.wolox.challenge.repositories.AlbumRepository;
import com.wolox.challenge.repositories.AlbumUserRepository;
import com.wolox.challenge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    private static final AlbumUserTransform ALBUM_USER_TRANSFORM = new AlbumUserTransform();
    private static final UserTransform USER_TRANSFORM = new UserTransform();

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
    public CompletableFuture<Optional<AlbumUserDTO>> sharedAlbumWithUser(Album album, User user, Long accessTypeId) {
        AccessType accessType = AccessType.getByIdAccess(accessTypeId);

        if (accessType.equals(AccessType.UNKNOWN)) {
            throw new ResourceNotFoundException(AccessType.class.toString(), "id", accessTypeId);
        }

        // The permission will be saved if the record does not exist in AlbumUser
        // and it is not shared with the owner
        if (!this.existsAlbumUser(album.getEndpointId(), user.getEndpointId())
                && !album.getOwnerEndpointId().equals(user.getEndpointId())) {

            user = userVerified(user);
            album = albumVerified(album);

            AlbumUser albumUser = AlbumUser.builder().user(user).album(album).accessType(accessType).build();

            return CompletableFuture.completedFuture(Optional.of(ALBUM_USER_TRANSFORM.AlbumUserToAlbumUserDTO(albumUserRepository.save(albumUser))));
        }

        return CompletableFuture.completedFuture(Optional.empty());
    }

    public Optional<AlbumUserDTO> updateAccessToAlbum(Long albumId, Long userId, Long accessTypeId) {
        AccessType accessType = AccessType.getByIdAccess(accessTypeId);

        if (accessType.equals(AccessType.UNKNOWN)) {
            throw new ResourceNotFoundException(AccessType.class.toString(), "id", accessTypeId);
        }

        AlbumUser albumUser = albumUserRepository.findByAlbum_EndpointIdAndUser_EndpointId(albumId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        AlbumUser.class.toString(), "id", (albumId + ", " + userId)));


        AlbumUser albumUserSave = AlbumUser.builder()
                .album(albumUser.getAlbum())
                .user(albumUser.getUser())
                .accessType(accessType)
                .build();

        return Optional.of(ALBUM_USER_TRANSFORM.AlbumUserToAlbumUserDTO(albumUserRepository.save(albumUserSave)));
    }

    public Optional<List<UserDTO>> getAllUsersByAlbumAndAccessType(Long albumId, Long accessTypeId) {
        AccessType accessType = AccessType.getByIdAccess(accessTypeId);

        if (accessType.equals(AccessType.UNKNOWN)) {
            throw new ResourceNotFoundException(AccessType.class.toString(), "id", accessTypeId);
        }

        return Optional.of(
                userRepository.findByAlbumIdAndAccessType(albumId, accessType).stream()
                        .map(USER_TRANSFORM::userToUserDTO)
                        .collect(Collectors.toList())
        );
    }

    private Boolean existsAlbumUser(Long albumId, Long userId) {
        return albumUserRepository.existsAlbumUserByAlbum_IdAndUser_Id(albumId, userId);
    }

    private User userVerified(User user) {
        Optional<User> userVerified = userRepository.findByEndpointId(user.getEndpointId());

        return userVerified.orElseGet(() -> userRepository.save(user));
    }

    private Album albumVerified(Album album) {
        Optional<Album> albumVerified = albumRepository.findByEndpointId(album.getEndpointId());

        return albumVerified.orElseGet(() -> albumRepository.save(album));
    }
}