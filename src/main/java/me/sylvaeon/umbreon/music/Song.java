package me.sylvaeon.umbreon.music;

import javafx.scene.media.AudioTrack;

public class Song {
    String name;
    String URL;
    AudioTrack audioTrack;

    public Song(String name, String URL, AudioTrack audioTrack) {
        this.name = name;
        this.URL = URL;
        this.audioTrack = audioTrack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public AudioTrack getAudioTrack() {
        return audioTrack;
    }

    public void setAudioTrack(AudioTrack audioTrack) {
        this.audioTrack = audioTrack;
    }
}
