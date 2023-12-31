package com.example.newsappkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.newsappkotlin.databinding.ActivityCentralAppBinding
import com.example.newsappkotlin.fragments.FavoritesFragment
import com.example.newsappkotlin.fragments.HomeFragment
import com.example.newsappkotlin.fragments.ProfileFragment
import com.example.newsappkotlin.fragments.SavedArticlesFragment

class CentralAppActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCentralAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityCentralAppBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_central_app)
        ///TODO : userId is to be got from the login activity
        val userId:Int = 1
        replaceFragment(HomeFragment(userId))
        binding.bottomNavigationView.setOnItemReselectedListener {
            when(it.itemId){
                R.id.home ->{
                    Log.v("matrab","Clicked Home")
                    replaceFragment(HomeFragment(userId))
                }
                R.id.favorits ->{
                    Log.v("matrab","Clicked Favorits")
                    replaceFragment(FavoritesFragment(userId))
                }
                R.id.saved ->{
                    Log.v("matrab","Clicked SavedArticles")
                    replaceFragment(SavedArticlesFragment(userId))
                }
                R.id.profile ->{
                    Log.v("matrab","Clicked Profile")
                    replaceFragment(ProfileFragment(userId))
                }
                else ->{
                    Log.v("matrab","Clicked None")
                }
            }
            true
        }
    }
    fun replaceFragment(fragment:Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
    }
}