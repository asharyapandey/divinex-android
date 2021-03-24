package com.asharya.divinex.api

import com.asharya.divinex.model.User
import com.asharya.divinex.response.LoginResponse
import com.asharya.divinex.response.SearchResponse
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

    @GET("user/{id}")
    suspend fun getUserById(
        @Header("auth-token") token: String,
        @Path("id") id: String
    ) : Response<UserResponse>

    @GET("user/search")
    suspend fun getSearchUsers(
        @Header("auth-token") token: String,
        @Query("term") term: String
    ) : Response<SearchResponse>

    @POST("user/follow/{id}")
    suspend fun followUser(
        @Header("auth-token") token: String,
        @Path("id") id: String
    ) : Response<LoginResponse>
}