package com.example.newsappkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val userId = intent.getIntExtra("userId", -1)
        val articleId = intent.getStringExtra("articleId")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
    }
}