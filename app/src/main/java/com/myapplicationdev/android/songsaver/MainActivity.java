package com.myapplicationdev.android.songsaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button show, insert;
    EditText editTitle, editSingers, editYear;
    RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = findViewById(R.id.showList);
        insert = findViewById(R.id.insert);

        editTitle = findViewById(R.id.editTitle);
        editSingers = findViewById(R.id.editSingers);
        editYear = findViewById(R.id.editYear);

        rating = findViewById(R.id.rating);

        DBHelper db = new DBHelper(MainActivity.this);

        insert.setOnClickListener(v -> {
            String title = editTitle.getText().toString();
            String singers = editSingers.getText().toString();
            int year = Integer.parseInt(editYear.getText().toString());
            int stars = (int) rating.getRating();

            Log.i("MainActivity.java", title + ", " + singers + ", " + year + ", " + stars + ", ");
            boolean valid = true;

            if(title.isEmpty()){
                editTitle.setError("Cannot be empty");
                valid = false;
            } if(singers.isEmpty()){
                editSingers.setError("Cannot be empty");
                valid = false;
            } if (year<0){
                editYear.setError("Cannot be empty");
                valid = false;
            }
            if(valid) {
                db.insertSong(title, singers, year, stars);
                Log.i("insert Main Activity", "inserted successfully");
                Toast.makeText(this, "Inserted Song Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        show.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ShowSongListActivity.class)));



    }
}