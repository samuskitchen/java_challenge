package com.wolox.challenge.repositories;

import com.wolox.challenge.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Optional<Album> findByEndpointId(Long endpointId);
}
