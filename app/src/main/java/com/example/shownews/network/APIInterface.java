package com.example.shownews.network;

import com.example.shownews.network.responce_structures.ResponseStructure;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Интерфейс с описанием запросов к API
public interface APIInterface {
    @GET("everything?sortBy=publishedAt")
    Call<ResponseStructure> getNews(@Query("q") String qValue, @Query("from") String date, @Query("apiKey") String apiKey);
}
