package com.myapplicationdev.android.songsaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nuur Aisyah Binte Farouk on 17/7/2023.
 * C346-1D-E63A-A
 */
public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Song> songsList;

    public CustomAdapter(Context context, int resource, ArrayList<Song> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        songsList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Obtain LayoutInflater object
        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //"Inflate" the view for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        //Obtain Ui components and do necessary binding
        TextView tvName = rowView.findViewById(R.id.tvName);
        TextView tvYear = rowView.findViewById(R.id.tvYear);
        TextView tvStars = rowView.findViewById(R.id.tvStars);
        TextView tvSingers = rowView.findViewById(R.id.tvSingers);



        //Obtain the andorid version information based on positon
        Song selectedSong = songsList.get((position));

        //Set values to textview to display the corresponging information
        tvName.setText(selectedSong.getId() + ": " + selectedSong.getTitle());
        tvYear.setText(String.valueOf(selectedSong.getYear()));
        tvStars.setText(selectedSong.starsToString());
        tvSingers.setText(selectedSong.getSingers());

        return rowView;
    }
}
