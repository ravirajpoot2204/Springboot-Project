package com.videostream.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.videostream.helper.CustomMessage;
import com.videostream.model.Video;
import com.videostream.services.VideoService;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    

    private VideoService videoService;

 
    public VideoController(VideoService videoService) {
	this.videoService = videoService;
    }


    @PostMapping
    public ResponseEntity<?> create(
	    @RequestParam("file") MultipartFile file, 
	    @RequestParam("title")String title, 
	    @RequestParam("description")String decription){
	Video video = new Video();
	
	video.setTitle(title);
	video.setDescription(decription);
	video.setVideoId(UUID.randomUUID().toString());
	Video save = videoService.save(video, file);

	if(save!=null) {
	    return ResponseEntity
		    .status(HttpStatus.OK)
		    .body(video);
	}else {
	    return ResponseEntity
		    .status(HttpStatus.INTERNAL_SERVER_ERROR)
		    .body( new CustomMessage("Video could not be uploaded...",false))
			    ;   
	}
	
	
	
    }
    
}
