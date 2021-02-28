package com.asharya.divinex.api

import com.asharya.divinex.model.User
import com.asharya.divinex.response.LoginResponse
import com.asharya.divinex.response.UserResponse
import retrofit2.Response
import retrofit2.http.*

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

    @GET("user")
    suspend fun getCurrentUser(
        @Header("auth-token") token: String
    ) : Response<UserResponse>

}