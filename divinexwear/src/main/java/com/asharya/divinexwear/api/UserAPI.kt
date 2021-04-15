package com.asharya.divinex.api

import com.asharya.divinex.model.User
import com.asharya.divinex.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @DELETE("user/unfollow/{id}")
    suspend fun unFollowUser(
        @Header("auth-token") token: String,
        @Path("id") id: String
    ) : Response<LoginResponse>

    @Multipart
    @PUT("user/{id}")
    suspend fun updateProfile(
        @Header("auth-token") token: String,
        @Path("id") id: String,
        @Part("email") email: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<UserResponse>

    @FormUrlEncoded
    @PUT("user/{id}")
    suspend fun updateProfile(
        @Header("auth-token") token: String,
        @Path("id") id: String,
        @Field("email") email: String,
        @Field("gender") gender: String
    ): Response<UserResponse>

    @GET("user/notification")
    suspend fun getNotifications(
        @Header("auth-token") token: String,
    ): Response<NotificationResponse>
}