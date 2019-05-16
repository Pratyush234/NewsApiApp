package com.example.praty.newsapiapp.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.praty.newsapiapp.model.OfflineNews;

import java.util.List;


//data access objects interface, part of the Room persistence that implements SQLite operations using annotations
@Dao
public interface NewsDao {

    @Insert
    long[] insertOfflineNews(OfflineNews... news);

    @Query("SELECT * FROM news")
    LiveData<List<OfflineNews>> getOfflineNews();

    @Query("DELETE FROM news")
    void delete();
}
