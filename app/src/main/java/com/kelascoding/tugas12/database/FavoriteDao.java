package com.kelascoding.tugas12.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.kelascoding.tugas12.model.Movie;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addMovie(Movie movie);

    @Query("select * from movielist")
    List<Movie> getFavoriteData();

    @Delete
    void deleteMovie(Movie movie);

    @Update
    void updateMovie(Movie movie);

    @Query("DELETE FROM movielist")
    void nukeTable();
}
