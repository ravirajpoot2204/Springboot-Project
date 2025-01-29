package com.videostream.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="yt-Playlists")
public class PlayLists {

    @Id
    private String id;
    private String title;
    /*
     * @OneToMany(mappedBy = "playLists") private List<Video> list = new
     * ArrayList<>() ;
     */
}
