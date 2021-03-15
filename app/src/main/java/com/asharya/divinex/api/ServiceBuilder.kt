package com.asharya.divinex.api

import com.asharya.divinex.model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL = "http://10.0.2.2:5000/api/"
//    private const val BASE_URL = "http://localhost:5000/api/"

    var token : String? = null
    var currentUser: User? = null

    // logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttp = OkHttpClient.Builder().addInterceptor(logger)

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

    fun loadImagePath(): String {
        val arr = BASE_URL.split("/").toTypedArray()
        return arr[0] + "/" + arr[1] + arr[2] + "/"
    }
}