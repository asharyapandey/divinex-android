package com.asharya.divinex.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL = "http://10.0.2.2:5000/api/"
//    private const val BASE_URL = "http://localhost:5000/api/"



    val token : String? = null

    private val okHttp = OkHttpClient.Builder()

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    // retrofit instance
    private val retrofit = retrofitBuilder.build()

    // generic function
    fun <T> buildService(serviceType: Class<T>) : T {
        return retrofit.create(serviceType)
    }
}