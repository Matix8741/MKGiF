package com.metody.mkgif;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.metody.mkgif.data.tools.DataStatus;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final ViewFlipper vf = findViewById(R.id.vf);
        TextView creator = null;
        TextView createDate = null;
        TextView brand = null;
        switch ((DataStatus)getIntent().getSerializableExtra("type")){
            case Book:
                vf.setDisplayedChild(1);

                creator = findViewById(R.id.AuthorInput);
                createDate= findViewById(R.id.Book_DateInput);
                brand = findViewById(R.id.Book_BrandInput);
                break;
            case Game:
                vf.setDisplayedChild(0);
                creator = findViewById(R.id.StudioInput);
                createDate= findViewById(R.id.Game_DateInput);
                brand = findViewById(R.id.Game_BrandInput);
                break;
            case Movie:
                vf.setDisplayedChild(2);
                creator = findViewById(R.id.DirectorInput);
                createDate= findViewById(R.id.Movie_DateInput);
                brand = findViewById(R.id.Movie_BrandInput);
                break;
        }
        final TextView title = findViewById(R.id.addTextField);
        title.setText(getIntent().getStringExtra("text"));
        creator.setText(getIntent().getStringExtra("creator"));
        createDate.setText(getIntent().getStringExtra("createDate"));
        brand.setText(getIntent().getStringExtra("brand"));
        brand.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        RatingBar ratingBar = findViewById(R.id.rating);
        ratingBar.setRating(Float.valueOf(getIntent().getStringExtra("rating")));

    }
}
