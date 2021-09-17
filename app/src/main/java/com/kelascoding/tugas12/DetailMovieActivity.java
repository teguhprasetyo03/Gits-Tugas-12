package com.kelascoding.tugas12;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kelascoding.tugas12.adapter.MovieAdapter;
import com.kelascoding.tugas12.model.Movie;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailMovieActivity extends AppCompatActivity {
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//        toolBarLayout.setTitle(getTitle());

        Bundle bundle = getIntent().getBundleExtra(MovieAdapter.DATA_EXTRA);
        movie = bundle.getParcelable(MovieAdapter.DATA_MOVIE);

        toolBarLayout.setTitle(movie.getTitle());

        ImageView imgDetail = findViewById(R.id.img_movie_detail);
        TextView tvDetailTitle = findViewById(R.id.tv_title_detail);
        TextView tvTglRilis = findViewById(R.id.tv_tgl_rilis_detail);
        TextView tvRatings = findViewById(R.id.tv_rating_detail);
        TextView tvDesc = findViewById(R.id.tv_desc_movie_detail);

        Picasso.get()
                .load("https://image.tmdb.org/t/p/w300_and_h450_bestv2" + movie
                        .getBackdropPath()).into(imgDetail);

        tvDetailTitle.setText(movie.getTitle());
        tvTglRilis.setText(movie.getReleaseDate());
        tvRatings.setText(movie.getVoteAverage().toString());
        tvDesc.setText(movie.getOverview());
    }
}