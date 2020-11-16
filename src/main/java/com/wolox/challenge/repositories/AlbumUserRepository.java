package com.wolox.challenge.repositories;

import com.wolox.challenge.models.AlbumUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumUserRepository extends JpaRepository<AlbumUser, Long> {

    Optional<AlbumUser> findByAlbum_EndpointIdAndUser_EndpointId(Long albumId, Long userId);

    Boolean existsAlbumUserByAlbum_IdAndUser_Id(Long albumId, Long userId);
}
