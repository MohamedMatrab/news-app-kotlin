package com.example.newsappkotlin.dataApi

data class MyData(
    val nextPage: String,
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)