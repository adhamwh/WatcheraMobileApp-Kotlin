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

        binding.profileLayout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.profileLayout.setOnClickListener {
            Log.d("BTN_CLICK", "✅ Profile clicked")
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.imageViewHome.setOnClickListener {
            Log.d("BTN_CLICK", "🏠 Home clicked")
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
                val rawUrl = banners[0].url
                val cleanName = rawUrl
                    .replace("drawable://", "")
                    .replace(".png", "")
                    .replace(".webp", "")
                    .replace(".jpg", "")

                val resId = resources.getIdentifier(cleanName, "drawable", packageName)

                Log.d("MainActivity", "Banner loading: $rawUrl → Clean: $cleanName → ResID: $resId")

                if (resId != 0) {
                    binding.banner.setImageResource(resId)
                } else {
                    Log.e("MainActivity", "Banner image not found: $cleanName")
                    binding.banner.setImageResource(R.drawable.placeholder) // optional fallback
                }
            } else {
                Log.w("MainActivity", "No banners found.")
                binding.banner.setImageResource(R.drawable.placeholder) // optional
            }

            binding.progressBarBanner.visibility = View.GONE
        }
    }


    //private fun initBanner() {
       // binding.progressBarBanner.visibility = View.VISIBLE
       // viewModel.loadBanner().observeForever { banners ->
          //  if (banners.isNotEmpty()) {
            //    Glide.with(this)
              //      .load(banners[0].url.replace("drawable:/banner", ""))
                //    .into(binding.banner)
         //   } else {
            //    Log.w("initBanner", "No banners found.")
                // Optionally show a default image or error message
         //   }
          //  binding.progressBarBanner.visibility = View.GONE
      //  }
    //}

}