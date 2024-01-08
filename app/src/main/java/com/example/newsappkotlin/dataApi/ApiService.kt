package com.example.newsappkotlin.dataApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/1/news")
    fun getNews(
        @Query("country") country: String = "us",
        @Query("apikey") apiKey: String = "pub_35650fafd8da89d473d7ec184a252757463ff"
    ): Call<MyData>
}