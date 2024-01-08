package com.example.newsappkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.newsappkotlin.R
import com.example.newsappkotlin.dataApi.Article
import com.example.newsappkotlin.dataApi.MyData
import com.example.newsappkotlin.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment(val userId: String) : Fragment() {

    private var articles: List<Article>? = null
    private lateinit var call: Call<MyData>
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        getData() // Call the getData() method to initiate the network request

        binding.titleHome.text = userId




        return binding.root
    }

    private fun getData() {
        call = RetrofitInstance.apiService.getNews()

        call.enqueue(object : Callback<MyData> {
            override fun onResponse(call: Call<MyData>, response: Response<MyData>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    articles = newsResponse?.articles
                    binding.titleHome.text = "I Got ${newsResponse?.totalResults}"

                } else {
                    // Handle unsuccessful response
                    // You might want to provide some feedback to the user
                    showToast("Network request failed. On Response.")
                }
            }

            override fun onFailure(call: Call<MyData>, t: Throwable) {
                // Handle failure
                // You might want to provide some feedback to the user
                showToast("Network request failed. $t.")
                binding.titleHome.text = t.toString()
            }

        })
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
