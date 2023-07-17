package com.myapplicationdev.android.songsaver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowSongListActivity extends AppCompatActivity {

    ListView listview;
    Button back;
    ToggleButton fiveStars;

    Spinner yearSpinner;
    ArrayList<Song> songAL;
    ArrayList<Integer> yearAL;

    DBHelper dbh;
    ArrayAdapter<?> yearAA;
    CustomAdapter songAA;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_song_list);
        listview = findViewById(R.id.listView);

        back = findViewById(R.id.back);
        fiveStars = findViewById(R.id.fiveStars);
        yearSpinner = findViewById(R.id.yearSpinner);

        dbh = new DBHelper(ShowSongListActivity.this);
        songAL = dbh.getSongs();
        songAA = new CustomAdapter(this, R.layout.row, songAL);

        listview.setAdapter(songAA);
        retrieveSongs();

        yearAA = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, yearAL);
        yearAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAA);

        listview.setOnItemClickListener((parent, view, position, id) -> {
            Song data = songAL.get(position);
            Intent i = new Intent(ShowSongListActivity.this, EditSongsActivity.class);
            i.putExtra("data", data);
            startActivity(i);
        });

        back.setOnClickListener(v -> startActivity(new Intent(ShowSongListActivity.this, MainActivity.class)));

        fiveStars.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                songAL.clear();
                songAL.addAll(dbh.getSongsWithRating(5));

                songAA.notifyDataSetChanged();
                yearAA.notifyDataSetChanged();
                dbh.close();
                Toast.makeText(ShowSongListActivity.this, "Five stars", Toast.LENGTH_SHORT).show();

            }else{
                retrieveSongs();
                Toast.makeText(ShowSongListActivity.this, "Normal stars", Toast.LENGTH_SHORT).show();
            }
        });
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int filterYear = yearAL.get(position);
                if (filterYear == 0) {
                    retrieveSongs();
                    Toast.makeText(ShowSongListActivity.this, "Showing All", Toast.LENGTH_SHORT).show();
                }else{
                    songAL.clear();
                    songAL.addAll(dbh.getSongsWithYear((int) filterYear));

                    songAA.notifyDataSetChanged();
                    dbh.close();
                    Toast.makeText(ShowSongListActivity.this, "Showing songs from: " + filterYear, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveSongs();
        songAA.notifyDataSetChanged();
        yearAA.notifyDataSetChanged();
    }

    public void retrieveSongs() {
        songAL.clear();
        songAL.addAll(dbh.getSongs());
        yearAL = new ArrayList<>();
        for (Song song: songAL) {
            yearAL.add(song.getYear());
        }
        yearAL.add(0);
        songAA.notifyDataSetChanged();
        dbh.close();
    }
}