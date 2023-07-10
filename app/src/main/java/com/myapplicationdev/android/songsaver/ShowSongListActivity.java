package com.myapplicationdev.android.songsaver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowSongListActivity extends AppCompatActivity {

    ListView listview;
    Button back;
    ToggleButton fiveStars;
    ArrayList<Song> al;

    DBHelper dbh;
    ArrayAdapter<?> aa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_song_list);
        listview = findViewById(R.id.listView);

        back = findViewById(R.id.back);
        fiveStars = findViewById(R.id.fiveStars);

        dbh = new DBHelper(ShowSongListActivity.this);
        al = dbh.getSongs();
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, al);

        listview.setAdapter(aa);
        retrieveSongs();

        listview.setOnItemClickListener((parent, view, position, id) -> {
            Song data = al.get(position);
            Intent i = new Intent(ShowSongListActivity.this, EditSongsActivity.class);
            i.putExtra("data", data);
            startActivity(i);
        });

        back.setOnClickListener(v -> startActivity(new Intent(ShowSongListActivity.this, MainActivity.class)));

        fiveStars.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                al.clear();
                al.addAll(dbh.getSongs(5));

                dbh.close();
                for (int i = 0; i < al.size(); i++) {
                    al.get(i).setId(i);
                    Log.i("song " + i, al.get(i).toString() + " " +al.get(i).getId());
                }
                aa.notifyDataSetChanged();
                dbh.close();
                Toast.makeText(ShowSongListActivity.this, "Five stars", Toast.LENGTH_SHORT).show();

            }else{
                retrieveSongs();
                Toast.makeText(ShowSongListActivity.this, "Normal stars", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveSongs();
    }

    public void retrieveSongs() {
        al.clear();
        al.addAll(dbh.getSongs());
        aa.notifyDataSetChanged();
        dbh.close();
        for (int i = 0; i < al.size(); i++) {
            al.get(i).id = i;
            Log.i("song " + i, al.get(i).toString());
        }
    }
}