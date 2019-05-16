package com.example.praty.newsapiapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.example.praty.newsapiapp.asynctask.DeleteAsyncTask;
import com.example.praty.newsapiapp.asynctask.InsertAsyncTask;
import com.example.praty.newsapiapp.model.OfflineNews;
import com.example.praty.newsapiapp.persistence.NewsDatabase;

import java.util.List;

//intermediate class for room persistence and the viewmodel
public class OfflineNewsRepository {

    private NewsDatabase mDatabase;

    public OfflineNewsRepository(Context context){
        mDatabase=NewsDatabase.getInstance(context);
    }

    public void insertTask(OfflineNews news){
        new InsertAsyncTask(mDatabase.getDao()).execute(news);

    }


    public LiveData<List<OfflineNews>> retrieveNotesTask(){
        return mDatabase.getDao().getOfflineNews();
    }

    public void deleteTask(){
        new DeleteAsyncTask(mDatabase.getDao()).execute();
    }
}
