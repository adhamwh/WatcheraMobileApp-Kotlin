package com.example.watchera.Adapter

import DetailActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.watchera.Domain.ItemsModel
import com.example.watchera.databinding.ViewholderPopularBinding

class PopularAdapter(val items: List<ItemsModel>) :
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

        // Load title and price
        holder.binding.titleTxt.text = item.title
        holder.binding.priceTxt.text = "$" + item.price.toString()

        // Load thumbnail image locally
        holder.binding.pic.loadLocalImage(item.thumbnail.replace("drawable://", ""), context)

        // Navigate to DetailActivity on item click
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("object", item)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

    // Helper function to load local images
    private fun ImageView.loadLocalImage(resourceName: String, context: Context) {
        val resourceId = context.resources.getIdentifier(resourceName, "drawable", context.packageName)
        setImageResource(resourceId)
    }
}