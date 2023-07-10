package com.myapplicationdev.android.songsaver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowSongListActivity extends AppCompatActivity {

    ListView listview;
    ArrayList<Song> al;

    DBHelper dbh;
    ArrayAdapter<?> aa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_song_list);
        listview = findViewById(R.id.listView);
        dbh = new DBHelper(ShowSongListActivity.this);
        al = new ArrayList<Song>();
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, al);

        listview.setAdapter(aa);
        retrieveSongs(al, aa);
        dbh.close();

        listview.setOnItemClickListener((parent, view, position, id) -> {
            Song data = al.get(position);
            Intent i = new Intent(ShowSongListActivity.this, EditSongsActivity.class);
            i.putExtra("data", data);
            startActivity(i);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveSongs(al, aa);
    }

    public void retrieveSongs(ArrayList<Song> songArrayList, ArrayAdapter arrayAdapter) {
        songArrayList.clear();
        songArrayList.addAll(dbh.getSongs());
        arrayAdapter.notifyDataSetChanged();
        for (int i = 0; i < songArrayList.size(); i++) {
            songArrayList.get(i).id = i;
            Log.i("song " + i, songArrayList.get(i).toString());
        }

    }
}