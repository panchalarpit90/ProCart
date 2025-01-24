package com.example.procart.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procart.network.Product
import com.example.procart.network.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class StatusApi { LOADING, ERROR, LOADED }

class OverviewViewModel : ViewModel() {
    private val _status = MutableLiveData<StatusApi>()
    val status: LiveData<StatusApi>
        get() = _status

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val _navigateToSelectedProduct = MutableLiveData<Product?>()
    val navigateToSelectedProduct: LiveData<Product?>
        get() = _navigateToSelectedProduct

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _status.postValue(StatusApi.LOADING)
                val result = ProductApi.retrofitService.getProducts(limit = 100)
                _products.postValue(result.products)
                _status.postValue(StatusApi.LOADED)
            } catch (e: Exception) {
                _status.postValue(StatusApi.ERROR)
            }
        }
    }

    fun displayPropertyDetails(product: Product) {
        _navigateToSelectedProduct.value = product
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProduct.value = null
    }
}
