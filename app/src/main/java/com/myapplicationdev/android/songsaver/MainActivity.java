package com.myapplicationdev.android.songsaver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class MainActivity extends AppCompatActivity {
    Button show, insert;
    EditText editTitle, editSingers, editYear;
    RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = findViewById(R.id.show);
        insert = findViewById(R.id.insert);

        editTitle = findViewById(R.id.editTitle);
        editSingers = findViewById(R.id.editSingers);
        editYear = findViewById(R.id.editYear);

        rating = findViewById(R.id.rating);

        insert.setOnClickListener(v -> {

        });
        show.setOnClickListener(v -> {

        });


    }
}