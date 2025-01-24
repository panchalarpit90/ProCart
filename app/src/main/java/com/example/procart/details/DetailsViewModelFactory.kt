package com.example.procart.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.procart.network.Product

class DetailsViewModelFactory(private val product: Product, private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(product, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}