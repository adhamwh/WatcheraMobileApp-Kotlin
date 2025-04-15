package com.example.watchera

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.Watchera.Database.DatabaseHelper
import com.example.watchera.Domain.ItemsModel
import com.example.watchera.MainActivity
import com.example.watchera.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SQLite Database
        val dbHelper = DatabaseHelper(this)
        if (!dbHelper.isDataPreloaded()) {
            preloadData(dbHelper)
        }


        val db = dbHelper.writableDatabase // CLEAR DATABASE
        db.delete("Banner", null, null)
        db.delete("Category", null, null)
        db.delete("Popular", null, null)
        db.delete("PopularImages", null, null)

        preloadData(dbHelper) // HONE IT LOADS THE DATABASE

        binding.startBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


    }

     private fun preloadData(dbHelper: DatabaseHelper) {
         // Insert Banner
         dbHelper.insertBanner("drawable://banner.png")

         // Insert Categories
         dbHelper.insertCategory(0, "Trending")
         dbHelper.insertCategory(1, "Men")
         dbHelper.insertCategory(2, "Women")
         dbHelper.insertCategory(3, "Classic")
         dbHelper.insertCategory(4, "Digital")

         // Insert Popular Items
         val item1 = ItemsModel(
             title = "Apple Watch Series+",
             description = "Larger display with up to 30 percent more screen space...",
             thumbnail = "drawable://watch0_0",
             picUrl = arrayListOf(
                 "drawable://watch0_1",
                 "drawable://watch0_2"
             ),
             price = 442.95,
             rating = 4.6
         )
         val itemId1 = dbHelper.insertPopular(item1)
         dbHelper.insertPopularImage(itemId1, "drawable://watch0_1.png")
         dbHelper.insertPopularImage(itemId1, "drawable://watch0_2.png")
         dbHelper.insertPopularImage(itemId1, "drawable://watch0_3.png")
         dbHelper.insertPopularImage(itemId1, "drawable://watch0_4.png")
         dbHelper.insertPopularImage(itemId1, "drawable://watch0_5.png")


         val item2 = ItemsModel(
             title = "Apple Watch MAX",
             description = "Powerful features to keep you connected, active, and healthy.",
             thumbnail = "drawable://watch4_0",
             picUrl = arrayListOf(
                 "drawable://watch4_1",
                 "drawable://watch4_2"
             ),
             price = 231.95,
             rating = 4.3
         )
         val itemId2 = dbHelper.insertPopular(item2)
         dbHelper.insertPopularImage(itemId2, "drawable://watch4_1.png")
         dbHelper.insertPopularImage(itemId2, "drawable://watch4_2.png")
         dbHelper.insertPopularImage(itemId2, "drawable://watch4_3.png")
         dbHelper.insertPopularImage(itemId2, "drawable://watch4_4.png")
         dbHelper.insertPopularImage(itemId2, "drawable://watch4_5.png")


         val item3 = ItemsModel(
             title = "Apple Watch PRO",
             description = "Apple watch she ostore she fa5em w fekher 3al e5er",
             thumbnail = "drawable://watch_5_0",
             picUrl = arrayListOf(
                 "drawable://watch_5_1",
                 "drawable://watch_5_2",
                 "drawable://watch_5_3",
                 "drawable://watch_5_4",
                 "drawable://watch_5_5"
             ),
             price = 232.32,
             rating = 4.4
         )
         val itemId3 = dbHelper.insertPopular(item3)
         dbHelper.insertPopularImage(itemId3, "drawable://watch_5_1.png")
         dbHelper.insertPopularImage(itemId3, "drawable://watch_5_2.png")
         dbHelper.insertPopularImage(itemId3, "drawable://watch_5_3.png")
         dbHelper.insertPopularImage(itemId3, "drawable://watch_5_4.png")
         dbHelper.insertPopularImage(itemId3, "drawable://watch_5_5.png")




         val item4 = ItemsModel(
             title = "Apple Watch BRO",
             description = "Apple watch she bromax she fa5em w fekher 3al e5er",
             thumbnail = "drawable://watch6_1_0",
             picUrl = arrayListOf(
                 "drawable://watch6_1_1",
                 "drawable://watch6_1_2",
                 "drawable://watch6_1_3",
                 "drawable://watch6_1_4",
                 "drawable://watch6_1_5"
             ),
             price = 505.5,
             rating = 5.0
         )
         val itemId4 = dbHelper.insertPopular(item4)
         dbHelper.insertPopularImage(itemId4, "drawable://watch_5_1.png")
         dbHelper.insertPopularImage(itemId4, "drawable://watch_5_2.png")
         dbHelper.insertPopularImage(itemId4, "drawable://watch_5_3.png")
         dbHelper.insertPopularImage(itemId4, "drawable://watch_5_4.png")
         dbHelper.insertPopularImage(itemId4, "drawable://watch_5_5.png")

     }
}