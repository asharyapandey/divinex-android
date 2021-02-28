package com.asharya.divinex.repository

import android.util.Log
import com.asharya.divinex.api.ApiRequest
import com.asharya.divinex.api.PostAPI
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.dao.PostDAO
import com.asharya.divinex.model.Post
import com.asharya.divinex.response.AddPostResponse
import com.asharya.divinex.response.PostsResponse
import okhttp3.MultipartBody
import java.lang.Exception

class PostRepository(private val postDAO: PostDAO) : ApiRequest() {
    private val postAPI = ServiceBuilder.buildService(PostAPI::class.java)

    // add post
    suspend fun addPost(caption: String, image: MultipartBody.Part) : AddPostResponse {
        return apiRequest {
            postAPI.addPost(ServiceBuilder.token!!, caption, image)
        }
    }
    suspend fun getPostFeed() : List<Post> {
        refreshPosts()
        return postDAO.getAllPosts()
    }

    private suspend fun refreshPosts() {
        try {
            val response =  apiRequest {
                postAPI.getPostFeed(ServiceBuilder.token!!)
            }
            if (response.success == true) {
                for (post in response.posts!!)
                    postDAO.addPost(post)
            }

        } catch (ex: Exception) {
            Log.e("PostRepo", ex.toString())
        }
    }

    suspend fun getUserPosts() : PostsResponse {
        return apiRequest {
            postAPI.getUsersPost(ServiceBuilder.token!!)
        }
    }
}