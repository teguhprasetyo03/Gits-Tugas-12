package com.kelascoding.tugas12.database;

import com.kelascoding.tugas12.*;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.kelascoding.tugas12.model.Converters;
import com.kelascoding.tugas12.model.Movie;

import retrofit2.Converter;

@Database(entities = {Movie.class}, version = 2)

@TypeConverters({Converters.class})
public abstract class FavoriteDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();

}
