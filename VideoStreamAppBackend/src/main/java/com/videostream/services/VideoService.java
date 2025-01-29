package com.videostream.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.videostream.model.Video;

public interface VideoService {

    //save video
    
    Video save(Video video ,MultipartFile file);
    
    
    //get video by id
    Video getById(String videoId);
    
    //get video by title
    
    Video getByTitle(String title);
    
    List<Video> getAll();
    
    
}
