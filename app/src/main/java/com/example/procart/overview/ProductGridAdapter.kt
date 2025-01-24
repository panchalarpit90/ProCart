package com.example.procart.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.procart.cart.SharedViewModel
import com.example.procart.databinding.GridItemViewBinding
import com.example.procart.network.Product

class ProductGridAdapter(
    private val sharedViewModel: SharedViewModel,
    private val onProductClick: (Product) -> Unit
) : ListAdapter<Product, ProductGridAdapter.ProductViewHolder>(DiffUtilCallback()) {

    class ProductViewHolder(private val binding: GridItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            product: Product,
            sharedViewModel: SharedViewModel,
            onProductClick: (Product) -> Unit
        ) {
            binding.product = product
            val isInCart = sharedViewModel.cartItems.value?.contains(product) == true
            if (isInCart) {
                binding.addToCartBtn.visibility = View.GONE
                binding.addMoreLayout.visibility = View.VISIBLE
                binding.tvCount.text = product.quantity.toString()
            } else {
                binding.addToCartBtn.visibility = View.VISIBLE
                binding.addMoreLayout.visibility = View.GONE
            }
            binding.addToCartBtn.setOnClickListener {
                sharedViewModel.addToCart(product)
                binding.addToCartBtn.visibility = View.GONE
                binding.addMoreLayout.visibility = View.VISIBLE
                binding.tvCount.text = product.quantity.toString()
            }

            binding.addIconBtn.setOnClickListener {
                val newQuantity = (product.quantity ?: 0) + 1
                sharedViewModel.updateProductQuantity(product, newQuantity)
                binding.tvCount.text = newQuantity.toString()
            }

            binding.minusIconBtn.setOnClickListener {
                val newQuantity = (product.quantity ?: 0) - 1
                sharedViewModel.updateProductQuantity(product, newQuantity)
                if (newQuantity <= 0) {
                    sharedViewModel.removeFromCart(product)
                    binding.addToCartBtn.visibility = View.VISIBLE
                    binding.addMoreLayout.visibility = View.GONE
                } else {
                    binding.tvCount.text = newQuantity.toString()
                }
            }
            binding.root.setOnClickListener {
                onProductClick(product)
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            GridItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product, sharedViewModel, onProductClick)
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}