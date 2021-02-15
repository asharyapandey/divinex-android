package com.asharya.divinex.api

import com.asharya.divinex.model.User
import com.asharya.divinex.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserAPI {

    // Registering User
    @POST("user")
    suspend fun registerUser(
        @Body user: User
    ) : Response<LoginResponse>

    // Login
    @FormUrlEncoded
    @POST("user/login")
    suspend fun loginUser(
        @Field("username") username : String,
        @Field("password") password : String
    ) : Response<LoginResponse>

}