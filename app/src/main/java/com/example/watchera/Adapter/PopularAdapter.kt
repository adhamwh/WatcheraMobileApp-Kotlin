package com.example.watchera.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.watchera.DetailActivity
import com.example.watchera.Domain.ItemsModel
import com.example.watchera.R
import com.example.watchera.databinding.ViewholderPopularBinding

class PopularAdapter(private val items: List<ItemsModel>) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(val binding: ViewholderPopularBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderPopularBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        // Set title and price
        holder.binding.titleTxt.text = item.title
        holder.binding.priceTxt.text = "$${item.price}"

        // Clean image name
        val rawName = item.thumbnail
        val cleanName = rawName
            .replace("drawable://", "")
            .replace(".png", "")
            .replace(".webp", "")
            .replace(".jpg", "")

        val resId = context.resources.getIdentifier(cleanName, "drawable", context.packageName)

        Log.d("PopularAdapter", "Loading image: $rawName → Clean: $cleanName → ResID: $resId")

        // Fallback to placeholder if image not found
        if (resId != 0) {
            holder.binding.pic.setImageResource(resId)
        } else {
            Log.e("PopularAdapter", "Image not found: $cleanName")
            holder.binding.pic.setImageResource(R.drawable.placeholder) // you can create placeholder.png
        }

        // On click → open DetailActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("object", item)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}
