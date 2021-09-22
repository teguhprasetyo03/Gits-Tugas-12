package com.kelascoding.tugas12.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kelascoding.tugas12.BuildConfig;
import com.kelascoding.tugas12.DetailMovieActivity;
import com.kelascoding.tugas12.R;
import com.kelascoding.tugas12.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public static final String DATA_MOVIE = "datamovie";
    public static final String DATA_EXTRA = "dataextra";
    private final List<Movie> movies;
    private final Context context;
    private final int rowLayout;
    public static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w342//";

    public MovieAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w300_and_h450_bestv2" + movies.get(position).getBackdropPath())
                .apply(new RequestOptions().override(200, 300)).into(holder.imgPoster);
        holder.tvRating.setText(movies.get(position).getVoteAverage().toString());
        holder.tvTitle.setText(movies.get(position).getTitle());
        holder.tvTglRilis.setText(movies.get(position).getReleaseDate());
        holder.tvDesc.setText(movies.get(position).getOverview());
        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, DetailMovieActivity.class);
            intent.putExtra("backdrop", movies.get(position).getBackdropPath());
            intent.putExtra("detail", movies.get(position).getOverview());
            intent.putExtra("title", movies.get(position).getTitle());
            intent.putExtra("date", movies.get(position).getReleaseDate());
            intent.putExtra("rating", movies.get(position).getVoteAverage().toString());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvDesc, tvRating, tvTglRilis;
        FloatingActionButton btnFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_movie);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTglRilis = itemView.findViewById(R.id.tv_tgl_rilis);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvDesc = itemView.findViewById(R.id.tv_desc_movie);
            btnFav = itemView.findViewById(R.id.btn_fav);
        }
    }
}
