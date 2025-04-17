package com.example.watchera.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.watchera.CartManager
import com.example.watchera.Domain.CartItem
import com.example.watchera.Domain.ItemsModel
import com.example.watchera.databinding.ViewholderCartBinding

class CartAdapter(
    private val items: MutableList<CartItem>
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(val binding: ViewholderCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderCartBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.titleCartTxt.text = item.title
        holder.binding.priceCartTxt.text = "$${item.price}"
        holder.binding.imageCart.setLocalImage(item.thumbnail, context)

        holder.binding.removeBtn.setOnClickListener {
            val removedItem = items[position]
            items.removeAt(position)
            CartManager.removeItem(removedItem)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)

            Toast.makeText(context, "${removedItem.title} removed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = items.size

    private fun ImageView.setLocalImage(drawableString: String, context: Context) {
        val cleanName = drawableString.replace("drawable://", "").replace(".png", "")
        val resourceId = context.resources.getIdentifier(cleanName, "drawable", context.packageName)
        setImageResource(resourceId)
    }
}
