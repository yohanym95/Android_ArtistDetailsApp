package com.example.yohan.firebaserealtimetest;

public class Artists {
    //objrct class for

    String artistId;
    String artistName;
    String artistGeneric;

    public Artists(){

    }

    public Artists(String artistId, String artistName, String artistGeneric) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGeneric = artistGeneric;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGeneric() {
        return artistGeneric;
    }
}
