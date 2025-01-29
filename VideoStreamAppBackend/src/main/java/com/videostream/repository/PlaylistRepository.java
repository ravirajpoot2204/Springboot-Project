package com.videostream.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.videostream.model.PlayLists;

public interface PlaylistRepository extends JpaRepository<PlayLists, String> {

}
