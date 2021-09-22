package com.kelascoding.tugas12.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelascoding.tugas12.R;
import com.kelascoding.tugas12.adapter.MovieAdapter;
import com.kelascoding.tugas12.model.Movie;
import com.kelascoding.tugas12.model.MovieResponse;
import com.kelascoding.tugas12.rest.MovieApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieFragment extends Fragment {

    private static final String TAG = MovieFragment.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private RecyclerView rvMovie = null;
    // Masukkan API key themoviedb.org API KEY di bawah ini
    private final static String API_KEY = "6bf46b70b03d82e0e3e841e78f9416a3";

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        rvMovie = view.findViewById(R.id.movie_fragment);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));

        connectAndGetApiData();

        return view;
    }

    public void connectAndGetApiData(){
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
                List<Movie> movies = response.body().getResults();
                rvMovie.setAdapter(new MovieAdapter(movies, R.layout.item_cardview, getActivity()));
                Log.d(TAG, "Jumlah film: " + movies.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }

}