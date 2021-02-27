package com.asharya.divinex.api

import com.asharya.divinex.model.Post
import com.asharya.divinex.response.AddPostResponse
import com.asharya.divinex.response.PostsResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface PostAPI {
    @Multipart
    @POST("post/")
    suspend fun addPost(
        @Header("auth-token") token: String,
        @Body post: Post,
        @Part("image") image: MultipartBody.Part
    ) : Response<AddPostResponse>

    @GET("post/feed")
    suspend fun getPostFeed(
        @Header("auth-token") token: String,
    ): Response<PostsResponse>

    @GET("post/")
    suspend fun getUsersPost(
        @Header("auth-token") token: String,
    ): Response<PostsResponse>



}