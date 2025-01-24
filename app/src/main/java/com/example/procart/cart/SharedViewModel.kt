package com.example.procart.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.procart.network.Product

class SharedViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<MutableList<Product>?>(mutableListOf())
    val cartItems: LiveData<MutableList<Product>?> get() = _cartItems
    fun addToCart(product: Product) {
        val currentCart = _cartItems.value ?: mutableListOf()
        if (!currentCart.contains(product)) {
            product.quantity = 1
            currentCart.add(product)
        }
        _cartItems.value = currentCart
    }

    fun updateProductQuantity(product: Product, newQuantity: Int) {
        val currentCart = _cartItems.value
        if (newQuantity <= 0) {
            currentCart?.remove(product)
        } else {
            product.quantity = newQuantity
        }
        _cartItems.value = currentCart
    }

    fun removeFromCart(product: Product) {
        val currentCart = _cartItems.value
        currentCart?.remove(product)
        _cartItems.value = currentCart
    }
}