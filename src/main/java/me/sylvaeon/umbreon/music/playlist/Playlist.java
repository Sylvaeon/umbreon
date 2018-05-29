package me.sylvaeon.umbreon.music.playlist;

import me.sylvaeon.umbreon.music.Song;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    List<Song> songList;

    public Playlist(Song... songs) {
        songList = new ArrayList<>(songs.length);
    }

    public void addSong(Song song) {
        songList.add(song);
    }

    public void removeSong(Song song) {
        songList.remove(song);
    }

    public void removeSong(int index) {
        songList.remove(index);
    }
}
