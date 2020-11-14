package com.wolox.challenge.repositories;

import com.wolox.challenge.models.User;
import com.wolox.challenge.models.enums.AccessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT " +
            "au.user " +
            "FROM AlbumUser AS au " +
            "WHERE au.album.id = :albumId " +
            "AND au.accessType = :accessTypeId")
    List<User> findByAlbumIdAndAccessType(@Param("albumId") Long albumId,
                                          @Param("accessTypeId") AccessType accessType);

    Optional<User> findByEndpointId(Long endpointId);
}
