package com.example.procart.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val availabilityStatus: String? = null,
    val brand: String? = null,
    val category: String? = null,
    val description: String? = null,
    val dimensions: Dimensions? = null,
    val discountPercentage: Double = 0.0,
    val id: Int = 0,
    val images: List<String> = emptyList(),
    val meta: Meta? = null,
    val minimumOrderQuantity: Int = 0,
    val price: Double = 0.0,
    val rating: Double = 0.0,
    val returnPolicy: String? = null,
    val reviews: List<Review> = emptyList(),
    val shippingInformation: String? = null,
    val sku: String? = null,
    val stock: Int = 0,
    val tags: List<String> = emptyList(),
    val thumbnail: String? = null,
    val title: String? = null,
    val warrantyInformation: String? = null,
    val weight: Int = 0,
    var quantity: Int? = null
) : Parcelable