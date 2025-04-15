package com.example.watchera

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchera.Adapter.PicsAdapter
import com.example.watchera.Domain.CartItem
import com.example.watchera.Domain.ItemsModel
import com.example.watchera.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle()
        initList()

        binding.button2.setOnClickListener {
            val cartItem = CartItem(
                title = item.title,
                price = item.price,
                thumbnail = item.thumbnail.replace("drawable://", "").replace(".png", "")
            )
            CartManager.addToCart(cartItem)
            Toast.makeText(this, "${item.title} added to cart", Toast.LENGTH_SHORT).show()
        }


        binding.button.setOnClickListener {
            val cartItem = CartItem(
                title = item.title,
                price = item.price,
                thumbnail = item.thumbnail.replace("drawable://", "").replace(".png", "")
            )
            CartManager.addToCart(cartItem)
            Toast.makeText(this, "${item.title} added to cart", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }



    }

    private fun initList() {
        val picList = ArrayList<String>()
        for (imageUrl in item.picUrl) {
            picList.add(imageUrl.replace("drawable://", "").replace(".png", ""))

        }

        // Load first image locally
        binding.img.loadLocalImage(picList[0], this)

        binding.picList.adapter = PicsAdapter(picList) { selectedImageUrl ->
            binding.img.loadLocalImage(selectedImageUrl, this)
        }
        binding.picList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun ImageView.loadLocalImage(resourceName: String, context: Context) {
        val resourceId = context.resources.getIdentifier(resourceName, "drawable", context.packageName)
        setImageResource(resourceId)
    }

    private fun bundle() {
        binding.apply {
            item = intent.getSerializableExtra("object") as ItemsModel

            // Load thumbnail locally
            val cleanThumb = item.thumbnail.replace("drawable://", "").replace(".png", "")
            img.loadLocalImage(cleanThumb, this@DetailActivity)


            titleTxt.text = item.title
            descriptionTxt.text = item.description
            priceTxt.text = "$" + item.price
            ratingTxt.text = item.rating.toString()

            backBtn.setOnClickListener { finish() }
        }
    }
}