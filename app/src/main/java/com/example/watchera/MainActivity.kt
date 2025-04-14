package com.example.watchera

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.Watchera.Database.DatabaseHelper
import com.example.watchera.Adapter.CategoryAdapter
import com.example.watchera.Adapter.PopularAdapter
import com.example.watchera.Domain.MainViewModel
import com.example.watchera.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel(DatabaseHelper(this)) // Pass SQLite helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanner()
        initCategory()
        initPopular()

        // Open LoginActivity when Profile ImageView is clicked
        binding.imageView3.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.loadPopular().observeForever { popularItems ->
            binding.recyclerViewPopular.layoutManager = GridLayoutManager(this, 2)
            binding.recyclerViewPopular.adapter = PopularAdapter(popularItems)
            binding.progressBarPopular.visibility = View.GONE
        }
    }

    private fun initCategory() {
        binding.progressBarCat.visibility = View.VISIBLE
        viewModel.loadCategory().observeForever { categories ->
            binding.recyclerViewCat.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerViewCat.adapter = CategoryAdapter(categories)
            binding.progressBarCat.visibility = View.GONE
        }
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.loadBanner().observeForever { banners ->
            if (banners.isNotEmpty()) {
                Glide.with(this)
                    .load(banners[0].url.replace("drawable:/banner", ""))
                    .into(binding.banner)
            } else {
                Log.w("initBanner", "No banners found.")
                // Optionally show a default image or error message
            }
            binding.progressBarBanner.visibility = View.GONE
        }
    }

}