package com.example.praty.newsapiapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.praty.newsapiapp.model.News;
import com.example.praty.newsapiapp.model.OfflineNews;
import com.example.praty.newsapiapp.repository.NewsRepository;
import com.example.praty.newsapiapp.repository.OfflineNewsRepository;

import java.util.List;

//viewmodel class for NewsActivity
public class NewsActivityViewModel extends ViewModel {

    private MutableLiveData<List<News>> mArticles;
    private LiveData<List<OfflineNews>> mOfflineArticles;
    private NewsRepository mNewsRepo;
    private OfflineNewsRepository mOfflineRepo;


    public void queryRepo(String country, String apiKey, Context context){
        mNewsRepo=NewsRepository.getInstance(context);
        mArticles=mNewsRepo.getNews(country, apiKey);
    }

    public void offlineRepo(Context context){
        mOfflineRepo=new OfflineNewsRepository(context);
        mOfflineArticles=mOfflineRepo.retrieveNotesTask();
    }

    public MutableLiveData<List<News>> getNews(){
        return mArticles;
    }

    public LiveData<List<OfflineNews>> getmOfflineArticles() {
        return mOfflineArticles;
    }


}
