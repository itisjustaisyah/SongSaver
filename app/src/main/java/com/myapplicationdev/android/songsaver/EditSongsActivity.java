package com.myapplicationdev.android.songsaver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditSongsActivity extends AppCompatActivity {
    Button update, delete, cancel;
    EditText updateTitle, updateSingers, updateYear, songId;
    RatingBar updateRating;
    Song data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_song);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        cancel = findViewById(R.id.cancel);
        songId = findViewById(R.id.editID);

        updateTitle = findViewById(R.id.updateTitle);
        updateSingers = findViewById(R.id.updateSingers);
        updateYear = findViewById(R.id.updateYear);

        updateRating = findViewById(R.id.updateRating);

        DBHelper dbh = new DBHelper(EditSongsActivity.this);
        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        preparingEditing(data);


        update.setOnClickListener(v -> {
            String title = updateTitle.getText().toString();
            String singers = updateSingers.getText().toString();
            int year = Integer.parseInt(updateYear.getText().toString());
            int stars = (int) updateRating.getRating();

            Log.i("MainActivity.java", title + ", " + singers + ", " + year + ", " + stars + ", ");
            boolean valid = true;

            if(title.isEmpty()){
                updateTitle.setError("Cannot be empty");
                valid = false;
            } if(singers.isEmpty()){
                updateSingers.setError("Cannot be empty");
                valid = false;
            } if (year < 0){
                updateYear.setError("Cannot be empty");
                valid = false;
            }
            if(valid) {
                if(dbh.updateSong(data ,new Song(title, singers, year, stars)) < 1){
                    Log.i("Edit Activity", "update unsuccessful");

                }else{
                    Log.i("Edit Activity", "update successfully");
                }

                Toast.makeText(this, "Updated Song Successfully", Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(EditSongsActivity.this, ShowSongListActivity.class));
        });
        cancel.setOnClickListener(v -> startActivity(new Intent(EditSongsActivity.this, ShowSongListActivity.class)));
        delete.setOnClickListener(v -> {
            dbh.deleteSong(data.getId());
            startActivity(new Intent(EditSongsActivity.this, ShowSongListActivity.class));
            Log.i("Edit Activty", "Deleted successfully");
            Toast.makeText(this, "Deleted Song Successfully", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        preparingEditing(data);
    }

    public void preparingEditing(Song data) {
        songId.setText(String.valueOf(data.getId()));
        updateTitle.setText(data.getTitle());
        updateSingers.setText(data.getSingers());
        updateYear.setText(String.valueOf(data.getYear()));
        updateRating.setRating(data.getStars());
    }
}