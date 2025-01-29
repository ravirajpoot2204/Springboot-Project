package com.videostream.services.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.videostream.model.Video;
import com.videostream.repository.VideoRepository;
import com.videostream.services.VideoService;

import jakarta.annotation.PostConstruct;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;
    
    @Value("${files.video}")
    String DIR;
    
    @PostConstruct
    public void init() {
	File file = new File(DIR);
	if(!file.exists()) {
	    file.mkdir();
	    System.out.println("Folder created..");
	}
	else {
	    System.out.println("Folder already exist : "+file.getPath());
	}
    }
    @Override
    public Video save(Video video, MultipartFile file) {
	try {

	    String fileName = file.getOriginalFilename();
	    String contentType = file.getContentType();
	    InputStream inputStream = file.getInputStream();

	    
	    // folder Path :create
	    
	    //this cleans path and files if extra characters comes in
	    String cleanFileName = org.springframework.util.StringUtils.cleanPath(fileName);
	    
	    String cleanFolder = StringUtils.cleanPath(DIR);
	    
	    // folder path with filename

	    Path path = Paths.get(cleanFolder,cleanFileName);
	   
	    System.out.println(path);
	    
	 
	    
	    // copy file to folder

	    Files.copy(inputStream, path,StandardCopyOption.REPLACE_EXISTING);
	    // video metadata create

	    video.setContentType(contentType);
	    video.setFilePath(path.toString());
	    // metadata save

	    return videoRepository.save(video);
	    
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}

	
    }

    @Override
    public Video getById(String videoId) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Video getByTitle(String title) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Video> getAll() {
	// TODO Auto-generated method stub
	return null;
    }

}
