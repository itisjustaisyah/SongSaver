package com.myapplicationdev.android.songsaver;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Nuur Aisyah Binte Farouk on 3/7/2023.
 * C346-1D-E63A-A
 */
public class Song implements Serializable {


    int id;
    String title;
    String singers;
    int year;
    int stars;

    public Song(String title, String singers, int year, int stars) {
        this.title = title;
        this.singers = singers;
        this.year = year;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSingers() {
        return singers;
    }

    public int getYear() {
        return year;
    }

    public int getStars() {
        return stars;
    }

    public String starsToString(){
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < stars; i++) {
            string.append("*");
        }
        return string.toString();
    }

    @NonNull
    @Override

    public String toString() {
        return  id + ". \nTitle: " + title + "\nSingers: " + singers+ "\nReleased: " + year+ "\nRating: " + starsToString();}
}
