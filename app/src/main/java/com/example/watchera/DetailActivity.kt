import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchera.Adapter.PicsAdapter
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
    }

    private fun initList() {
        val picList = ArrayList<String>()
        for (imageUrl in item.picUrl) {
            picList.add(imageUrl.replace("drawable://", ""))
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
            img.loadLocalImage(item.thumbnail.replace("drawable://", ""), this@DetailActivity)

            titleTxt.text = item.title
            descriptionTxt.text = item.description
            priceTxt.text = "$" + item.price
            ratingTxt.text = item.rating.toString()

            backBtn.setOnClickListener { finish() }
        }
    }
}