package com.example.praty.newsapiapp.retrofit;

import com.example.praty.newsapiapp.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<NewsResponse> getNewsArticles(@Query("country") String country, @Query("apiKey") String apiKey);
}
