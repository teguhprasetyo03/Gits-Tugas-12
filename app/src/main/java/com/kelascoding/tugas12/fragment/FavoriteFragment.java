package com.kelascoding.tugas12.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.kelascoding.tugas12.R;
import com.kelascoding.tugas12.adapter.MovieAdapter;
import com.kelascoding.tugas12.database.FavoriteDao;
import com.kelascoding.tugas12.database.FavoriteDatabase;
import com.kelascoding.tugas12.model.Movie;
import com.kelascoding.tugas12.model.MovieResponse;
import com.kelascoding.tugas12.rest.MovieApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoriteFragment extends Fragment {

    private static final String TAG = MovieFragment.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final Retrofit retrofit = null;
    // Masukkan API key themoviedb.org API KEY di bawah ini
    private final static String API_KEY = "6bf46b70b03d82e0e3e841e78f9416a3";

    private List<Movie> favMovie;
    private RecyclerView rvFavorite;
    MovieAdapter movieAdapter;
    public static FavoriteDatabase favoriteDatabase;
    Button clearList;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        Context context = view.getContext();
        rvFavorite = view.findViewById(R.id.rv_favorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));

        favoriteDatabase = Room.databaseBuilder(context, FavoriteDatabase.class, "favoritedb").allowMainThreadQueries().build();
        favMovie = favoriteDatabase.favoriteDao().getFavoriteData();
        rvFavorite.setAdapter(new MovieAdapter(favMovie, R.layout.item_cardview, getActivity()));

        clearList = view.findViewById(R.id.btn_clear_fav);
        clearList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearList();
            }
        });
        return view;
    }

    private void clearList() {
        favoriteDatabase.favoriteDao().nukeTable();
        favMovie = favoriteDatabase.favoriteDao().getFavoriteData();
        rvFavorite.setAdapter(new MovieAdapter(favMovie, R.layout.item_cardview, getActivity()));

        Toast.makeText(getContext(), "Favorites nuked", Toast.LENGTH_SHORT).show();
    }
}