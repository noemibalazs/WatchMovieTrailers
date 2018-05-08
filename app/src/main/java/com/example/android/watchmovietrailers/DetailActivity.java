package com.example.android.watchmovietrailers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mOverview;
    private TextView mReleaseDate;
    private TextView mUserRate;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitle = findViewById(R.id.tv_title);
        mUserRate = findViewById(R.id.tv_detail_user_rating);
        mReleaseDate = findViewById(R.id.tv_detail_release_date);
        mOverview = findViewById(R.id.tv_detail_description);
        mImage = findViewById(R.id.iv_detail_image);

        Intent intent = getIntent();
        if (intent == null){
            finish();
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }

        mTitle.setText(intent.getExtras().getString("Title"));
        mReleaseDate.setText(intent.getExtras().getString("Release"));
        mUserRate.setText(intent.getExtras().getString("Rate") + " / 10");
        mOverview.setText(intent.getExtras().getString("Overview"));

        Picasso.with(this).
                load(intent.getExtras().getString("Image"))
                .into(mImage);

    }

}
