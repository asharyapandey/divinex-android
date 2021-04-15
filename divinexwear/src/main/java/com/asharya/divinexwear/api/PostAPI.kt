package com.asharya.divinex.api

import com.asharya.divinex.entity.Comment
import com.asharya.divinex.model.Post
import com.asharya.divinex.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface PostAPI {
    @Multipart
    @POST("post")
    suspend fun addPost(
        @Header("auth-token") token: String,
        @Part("caption") caption: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<AddPostResponse>

    @GET("post/feed")
    suspend fun getPostFeed(
        @Header("auth-token") token: String,
    ): Response<PostsResponse>

    @GET("post/")
    suspend fun getUsersPost(
        @Header("auth-token") token: String,
    ): Response<PostsResponse>

    @GET("post/user/{id}")
    suspend fun getUsersPostById(
        @Header("auth-token") token: String,
        @Path("id") id: String
    ): Response<PostsResponse>

    @DELETE("post/{id}")
    suspend fun deletePost(
        @Header("auth-token") token: String,
        @Path("id") id: String
    ): Response<UpdateDeletePostResponse>

    @Multipart
    @PUT("post/{id}")
    suspend fun updatePost(
        @Header("auth-token") token: String,
        @Path("id") id: String,
        @Part("caption") caption: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<UpdateDeletePostResponse>


    @POST("post/comment/{id}")
    suspend fun addComment(
        @Header("auth-token") token: String,
        @Path("id") id: String,
        @Body comment: Comment
    ): Response<AddCommentResponse>

    @GET("post/comment/{id}")
    suspend fun getComments(
        @Header("auth-token") token: String,
        @Path("id") id: String
    ): Response<CommentResponse>

    @PUT("post/comment/{id}")
    suspend fun updateComment(
        @Header("auth-token") token: String,
        @Path("id") id: String,
        @Body comment: Comment
    ): Response<AddCommentResponse>

    @DELETE("post/comment/{id}")
    suspend fun deleteComment(
        @Header("auth-token") token: String,
        @Path("id") id: String,
    ): Response<AddCommentResponse>
}