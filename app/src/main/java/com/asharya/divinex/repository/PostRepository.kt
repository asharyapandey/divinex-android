package com.asharya.divinex.repository

import com.asharya.divinex.api.ApiRequest
import com.asharya.divinex.api.PostAPI
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.model.Post
import com.asharya.divinex.response.AddPostResponse
import com.asharya.divinex.response.PostsResponse
import okhttp3.MultipartBody

class PostRepository : ApiRequest() {
    private val postAPI = ServiceBuilder.buildService(PostAPI::class.java)

    // add post
    suspend fun addPost(caption: String, image: MultipartBody.Part) : AddPostResponse {
        return apiRequest {
            postAPI.addPost(ServiceBuilder.token!!, caption, image)
        }
    }
    suspend fun getPostFeed() : PostsResponse {
        return apiRequest {
            postAPI.getPostFeed(ServiceBuilder.token!!)
        }
    }

    suspend fun getUserPosts() : PostsResponse {
        return apiRequest {
            postAPI.getUsersPost(ServiceBuilder.token!!)
        }
    }
}