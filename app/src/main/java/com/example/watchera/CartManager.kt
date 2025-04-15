package com.example.watchera

import com.example.watchera.Domain.CartItem

object CartManager {
    val cartItems = mutableListOf<CartItem>()

    fun addToCart(item: CartItem) {
        cartItems.add(item)
    }

    fun getItems(): List<CartItem> = cartItems


    fun removeItem(item: CartItem) {
        cartItems.remove(item)
    }

    fun clearCart() {
        cartItems.clear()
    }
}