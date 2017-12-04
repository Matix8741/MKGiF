package com.metody.mkgif;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO do to use
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView editText = findViewById(R.id.StudioInput);
        TextView directorInfo = findViewById(R.id.DirectorInput);
        editText.setText(getIntent().getStringExtra("text"));
        directorInfo.setText(getIntent().getStringExtra("date"));
        RatingBar ratingBar = findViewById(R.id.rating);
        ratingBar.setRating(Float.valueOf(getIntent().getStringExtra("rating")));

    }
}
