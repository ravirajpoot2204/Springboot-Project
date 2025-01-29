package com.videostream.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.videostream.model.Video;

public interface VideoRepository extends JpaRepository<Video, String> {
    
   public Optional<Video> findByTitle(String title);

   //check later 1. query methods 2.native 3. criteria api
}
