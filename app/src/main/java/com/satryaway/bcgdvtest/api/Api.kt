package com.satryaway.bcgdvtest.api

import com.google.gson.GsonBuilder
import com.satryaway.bcgdvtest.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    val webService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(ApiService::class.java)
    }
}