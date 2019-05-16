package com.example.praty.newsapiapp.asynctask;

import android.os.AsyncTask;

import com.example.praty.newsapiapp.persistence.NewsDao;

//asynctask to perform background thread to delete all the records in the db
public class DeleteAsyncTask extends AsyncTask<Void,Void,Void> {

    private NewsDao mDao;

    public DeleteAsyncTask(NewsDao mDao) {
        this.mDao = mDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        mDao.delete();
        return null;
    }
}
