package com.example.praty.newsapiapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.praty.newsapiapp.R;
import com.example.praty.newsapiapp.model.News;
import com.example.praty.newsapiapp.model.NewsResponse;
import com.example.praty.newsapiapp.model.OfflineNews;
import com.example.praty.newsapiapp.retrofit.ApiClient;
import com.example.praty.newsapiapp.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//intermediate class to load data by parsing JSON of an API using retrofit, and saving it to room database
public class NewsRepository {
    private static final String TAG = "NewsRepository";

    private static NewsRepository instance;
    private static OfflineNewsRepository mRepo;
    private ApiInterface apiService= ApiClient.getclient().create(ApiInterface.class);

    public static NewsRepository getInstance(Context context){
        if(mRepo==null)
            mRepo=new OfflineNewsRepository(context);
        if(instance==null)
            instance=new NewsRepository();

        return instance;
    }

    public MutableLiveData<List<News>> getNews(String country, String apiKey){

        final MutableLiveData<List<News>> newsData=new MutableLiveData<>();
        Call<NewsResponse> call=apiService.getNewsArticles(country,apiKey);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                NewsResponse body=response.body();
                Log.d(TAG, "onResponse: called, body:"+body);
                if(body!=null){
                    newsData.setValue(body.getArticles());
                    mRepo.deleteTask();

                    //loop to save data to room database on a background thread
                    for(int i=0; i<body.getArticles().size();i++){
                        OfflineNews obj= new OfflineNews(body.getArticles().get(i).getTitle(),
                                body.getArticles().get(i).getDescription(),
                                body.getArticles().get(i).getAuthor(),
                                body.getArticles().get(i).getPublishedAt(),
                                body.getArticles().get(i).getUrl(),
                                body.getArticles().get(i).getUrlToImage()
                                );
                        mRepo.insertTask(obj);
                    }

                }

            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: error:"+t.toString());

            }
        });

        return newsData;
    }


}
