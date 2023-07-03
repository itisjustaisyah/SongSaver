package com.myapplicationdev.android.songsaver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowSongListActivity extends AppCompatActivity {

    ListView listview;

    DBHelper db = new DBHelper(ShowSongListActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_song_list);
        listview = findViewById(R.id.listView);

        ArrayList<Song> data = db.getSongs();
        ArrayAdapter<?> aaSongList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listview.setAdapter(aaSongList);
        db.close();
        for (int i = 0; i < data.size(); i++) {
            Log.d("Database Content", i + ". " + data.get(i));
        }
        aaSongList.notifyDataSetChanged();


    }
}