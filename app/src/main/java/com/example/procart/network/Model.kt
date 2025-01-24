package com.example.procart.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Model(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
) : Parcelable