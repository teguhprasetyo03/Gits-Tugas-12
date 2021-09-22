package com.kelascoding.tugas12;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kelascoding.tugas12.adapter.MovieAdapter;
import com.kelascoding.tugas12.database.FavoriteDatabase;
import com.kelascoding.tugas12.fragment.MovieFragment;
import com.kelascoding.tugas12.model.Movie;
import com.kelascoding.tugas12.model.MovieResponse;
import com.kelascoding.tugas12.rest.MovieApiService;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailMovieActivity extends AppCompatActivity {
    public static FavoriteDatabase favoriteDatabase;
    ArrayList<Movie> movieModels = new ArrayList<>();
    private static final String TAG = MovieFragment.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    // Masukkan API key themoviedb.org API KEY di bawah ini
    private final static String API_KEY = "6bf46b70b03d82e0e3e841e78f9416a3";
    ImageView imgDetail;
    TextView tvDetailTitle,tvTglRilis,tvRatings,tvDesc;
    public static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w342/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        initView();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
//        toolBarLayout.setTitle(getTitle());

        String backdropPath = getIntent().getStringExtra("backdrop");
        String detail = getIntent().getStringExtra("detail");
        String title = getIntent().getStringExtra("title");
        String date = getIntent().getStringExtra("date");
        String rating = getIntent().getStringExtra("rating");
        toolBarLayout.setTitle(title);

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w300_and_h450_bestv2" + backdropPath)
                .centerCrop().into(imgDetail);

        tvDetailTitle.setText(title);
        tvTglRilis.setText(date);
        tvRatings.setText(rating);
        tvDesc.setText(detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        favoriteDatabase = Room.databaseBuilder(getApplicationContext(), FavoriteDatabase.class, "favoritedb").allowMainThreadQueries().build();
        connectAndGetApiData();

        FloatingActionButton floatingActionButton = findViewById(R.id.btn_fav);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Movie> moviefav = favoriteDatabase.favoriteDao().getFavoriteData();

                for (Movie movie : movieModels) {
                    if (movie.getTitle().equals(title)) {

                        if (moviefav.size() != 0) {
                            boolean isExist = false;

                            for (Movie fav : moviefav) {
                                if (fav.getTitle().equals(movie.getTitle())) {
                                    isExist = true;
                                    break;
                                }
                            }

                            if (isExist) {
                                favoriteDatabase.favoriteDao().deleteMovie(movie);
                                Toast.makeText(view.getContext(), "Berhasil Menghapus Dari Favorit", Toast.LENGTH_SHORT).show();

                            } else {
                                favoriteDatabase.favoriteDao().addMovie(movie);
                                Toast.makeText(view.getContext(), "Berhasil Ditambah Ke Favorit", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            favoriteDatabase.favoriteDao().addMovie(movie);
                            Toast.makeText(view.getContext(), "Berhasil Ditambah Ke Favorit", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    }
                }

            }
        });

    }

    public void connectAndGetApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<MovieResponse> call = movieApiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, retrofit2.Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                Log.i("movie", Objects.requireNonNull(response.body()).getResults().toString());
                movieModels = (ArrayList<Movie>) response.body().getResults();
            }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }

    private void initView() {
        imgDetail = findViewById(R.id.detail_img);
       tvDetailTitle = findViewById(R.id.tv_title_detail);
        tvTglRilis = findViewById(R.id.tv_tgl_rilis_detail);
        tvRatings = findViewById(R.id.tv_rating_detail);
        tvDesc = findViewById(R.id.tv_desc_movie_detail);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}