package com.wolox.challenge.services;

import com.wolox.challenge.factories.AlbumFactory;
import com.wolox.challenge.factories.AlbumUserFactory;
import com.wolox.challenge.factories.UserFactory;
import com.wolox.challenge.models.Album;
import com.wolox.challenge.models.AlbumUser;
import com.wolox.challenge.models.User;
import com.wolox.challenge.models.dtos.AlbumUserDTO;
import com.wolox.challenge.models.dtos.UserDTO;
import com.wolox.challenge.models.enums.AccessType;
import com.wolox.challenge.repositories.AlbumRepository;
import com.wolox.challenge.repositories.AlbumUserRepository;
import com.wolox.challenge.repositories.UserRepository;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {

    private Album album;
    private User user;
    private List<User> userList = new ArrayList<>();
    private AlbumUser albumUser;
    private UserDTO userDTO;
    private List<UserDTO> userDTOList = new ArrayList<>();

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AlbumUserRepository albumUserRepository;

    @InjectMocks
    private AlbumService albumService;

    @BeforeEach
    void setUp() {
        album = new AlbumFactory().newInstance();

        user = new UserFactory().newInstance();
        userList.add(user);

        albumUser = new AlbumUserFactory().newInstance();

        userDTO = new UserFactory().newInstanceDTO();
        userDTOList.add(userDTO);
    }

    @Test
    void sharedAlbumWithUser() throws ExecutionException, InterruptedException {
        // Setup our mock repository
        lenient().when(albumUserRepository.existsAlbumUserByAlbum_IdAndUser_Id(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);

        lenient().when(userRepository.findByEndpointId(Mockito.anyLong())).thenReturn(Optional.of(user));
        lenient().when(userRepository.save(any(User.class))).thenReturn(user);

        lenient().when(albumRepository.findByEndpointId(Mockito.anyLong())).thenReturn(Optional.of(album));
        lenient().when(albumRepository.save(any(Album.class))).thenReturn(album);

        lenient().when(albumUserRepository.save(any(AlbumUser.class))).thenReturn(albumUser);

        // Execute the service call
        Optional<AlbumUserDTO> albumUserSave = albumService.sharedAlbumWithUser(album, user, 3L).get();

        // Assert the response
        Assertions.assertFalse(albumUserSave.isPresent(), "The returned entity should not be null");
    }

    @Test
    void updateAccessToAlbum() {
        // Setup our mock repository
        when(albumUserRepository.findByAlbum_EndpointIdAndUser_EndpointId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of(albumUser));
        when(albumUserRepository.save(any(AlbumUser.class))).thenReturn(albumUser);

        // Execute the service call
        Optional<AlbumUserDTO> albumUserDTO = albumService.updateAccessToAlbum(album.getEndpointId(), user.getEndpointId(), 2L);

        // Assert the response
        Assertions.assertTrue(albumUserDTO.isPresent(), "The returned entity should not be null");
    }

    @Test
    void getAllUsersByAlbumAndAccessType() {
        // Setup our mock repository
        when(userRepository.findByAlbumIdAndAccessType(Mockito.anyLong(), Mockito.any(AccessType.class))).thenReturn(userList);

        // Execute the service call
        Optional<List<UserDTO>> dtoList = albumService.getAllUsersByAlbumAndAccessType(album.getEndpointId(), 3L);

        // Assert the response
        Assertions.assertTrue(dtoList.isPresent(), "The returned entity should not be null");
        assertThat(dtoList.get(), not(IsEmptyCollection.empty()));
    }
}