package com.example.procart.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://dummyjson.com/"

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int): Model
}

object ProductApi {
    val retrofitService: ProductApiService

    init {
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofitService = retrofit.create(ProductApiService::class.java)
    }
}