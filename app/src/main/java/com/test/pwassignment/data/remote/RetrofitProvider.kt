package com.test.pwassignment.data.remote

import com.test.pwassignment.data.remote.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    private const val BASE_URL =
        "https://firebasestorage.googleapis.com/v0/b/user-contacts-ade83.appspot.com/o/"

    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
}
