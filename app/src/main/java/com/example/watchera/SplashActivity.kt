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
        preloadData(dbHelper)

        binding.startBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun preloadData(dbHelper: DatabaseHelper) {
        // Insert Banner
        dbHelper.insertBanner("drawable://banner")

        // Insert Categories
        dbHelper.insertCategory(0, "Trending")
        dbHelper.insertCategory(1, "Men")
        dbHelper.insertCategory(2, "Women")
        dbHelper.insertCategory(3, "Classic")
        dbHelper.insertCategory(4, "Digital")

        // Insert Popular Items
        val item1 = ItemsModel(
            title = "Apple Watch Series 10",
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
    }
}