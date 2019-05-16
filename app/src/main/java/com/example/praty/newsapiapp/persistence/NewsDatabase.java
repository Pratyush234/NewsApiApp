package com.example.praty.newsapiapp.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.praty.newsapiapp.model.OfflineNews;

//part of room persistence, used to declare a static method of DAO class and create an instance of Room database
@Database(entities = {OfflineNews.class}, version = 3)
public abstract class NewsDatabase extends RoomDatabase {

    private static final String DATABASE_NAME="news_db";

    private static NewsDatabase instance;

    public static NewsDatabase getInstance(final Context context){
        if(instance==null){
            instance= Room.databaseBuilder(
                    context.getApplicationContext(),
                    NewsDatabase.class,
                    DATABASE_NAME
            ).fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    public abstract NewsDao getDao();

}
