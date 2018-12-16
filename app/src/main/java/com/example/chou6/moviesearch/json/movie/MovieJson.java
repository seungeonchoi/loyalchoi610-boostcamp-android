package com.example.chou6.moviesearch.json.movie;

import java.util.ArrayList;

public class MovieJson {
    public String lastBuildDate;
    public String total;
    public String start;
    public String display;
    public ArrayList<MovieItem> items = new ArrayList<>();

    @Override
    public String toString() {
        return "MovieJson{" +
                "lastBuildDate='" + lastBuildDate + '\'' +
                ", total='" + total + '\'' +
                ", start='" + start + '\'' +
                ", display='" + display + '\'' +
                ", items=" + items +
                '}';
    }
}
