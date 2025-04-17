package com.example.watchera

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchera.Adapter.CartAdapter
import com.example.watchera.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cartItems = CartManager.getItems().toMutableList()
        val adapter = CartAdapter(cartItems)


        binding.recyclerViewOrder.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewOrder.adapter = adapter

        binding.placeOrderButton.setOnClickListener {
            val cardNumber = binding.cardNumberEdit.text.toString()
            val expiry = binding.expiryEdit.text.toString()
            val cvv = binding.cvvEdit.text.toString()
            val name = binding.cardNameEdit.text.toString()

            if (cardNumber.length == 16 && expiry.matches(Regex("\\d{2}/\\d{2}")) && cvv.length == 3 && name.isNotEmpty()) {
                Toast.makeText(this, "✅ Order Successful!", Toast.LENGTH_LONG).show()
                CartManager.clearCart()
                finish()
            } else {
                Toast.makeText(this, "❌ Invalid Payment Details", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
