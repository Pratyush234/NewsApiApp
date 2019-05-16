package com.example.praty.newsapiapp.asynctask;

import android.os.AsyncTask;

import com.example.praty.newsapiapp.model.OfflineNews;
import com.example.praty.newsapiapp.persistence.NewsDao;

//asynctask to perform insertion into the database table in a background thread
public class InsertAsyncTask extends AsyncTask<OfflineNews,Void,Void> {

    private NewsDao mDao;

    public InsertAsyncTask(NewsDao mDao) {
        this.mDao = mDao;
    }

    @Override
    protected Void doInBackground(OfflineNews... offlineNews) {
        mDao.insertOfflineNews(offlineNews);
        return null;
    }
}
