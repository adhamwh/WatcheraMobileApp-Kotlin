package com.example.watchera.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.watchera.R
import com.example.watchera.databinding.ViewholderPicBinding

class PicsAdapter(
    val items: MutableList<String>,
    val onImageSelected: (String) -> Unit
) : RecyclerView.Adapter<PicsAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    class ViewHolder(val binding: ViewholderPicBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderPicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = items[position]

        // Load image locally
        holder.binding.pic.loadLocalImage(item.replace("drawable://", ""), holder.itemView.context)

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)

            onImageSelected(item)
        }

        if (selectedPosition == position) {
            holder.binding.picLayout.setBackgroundResource(R.drawable.black_stroke_bg)
        } else {
            holder.binding.picLayout.setBackgroundResource(R.drawable.grey_stroke_bg)
        }
    }

    override fun getItemCount(): Int = items.size

    // Helper function to load local images
    private fun ImageView.loadLocalImage(resourceName: String, context: Context) {
        val resourceId = context.resources.getIdentifier(resourceName, "drawable", context.packageName)
        setImageResource(resourceId)
    }
}