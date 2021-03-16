package com.asharya.divinex.repository

import android.util.Log
import com.asharya.divinex.api.ApiRequest
import com.asharya.divinex.api.PostAPI
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.dao.PostDAO
import com.asharya.divinex.entity.Post
import com.asharya.divinex.entity.UserPost
import com.asharya.divinex.response.AddPostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception

class PostRepository(private val postDAO: PostDAO) : ApiRequest() {
    private val postAPI = ServiceBuilder.buildService(PostAPI::class.java)

    // add post
    suspend fun addPost(caption: RequestBody, image: MultipartBody.Part): AddPostResponse {
        return apiRequest {
            postAPI.addPost(ServiceBuilder.token!!, caption, image)
        }
    }

    suspend fun getPostFeed(): List<Post> {
        refreshPosts()
        return postDAO.getAllPosts()
    }

    private suspend fun refreshPosts() {
        try {
            val response = apiRequest {
                postAPI.getPostFeed(ServiceBuilder.token!!)
            }
            if (response.success == true) {
                for (post in response.posts!!)
                    postDAO.addPost(
                        Post(
                            _id = post._id,
                            caption = post.caption,
                            image = post.image,
                            userID = post.user?._id,
                            username = post.user?.username,
                            profilePicture = post.user?.profilePicture
                        )
                    )
            }

        } catch (ex: Exception) {
            Log.e("PostRepo", ex.toString())
        }
    }

    suspend fun getUserPosts(userID: String): List<Post> {
        refreshUserPosts(userID)
        return postDAO.getAllUserPost(userID)
    }

    private suspend fun refreshUserPosts(id: String) {
        try {
            val response = apiRequest {
                postAPI.getUsersPostById(ServiceBuilder.token!!, id)
            }
            if (response.success == true) {
                for (post in response.posts!!)
                    postDAO.addPost(
                        Post(
                            _id = post._id,
                            caption = post.caption,
                            image = post.image,
                            userID = post.user?._id,
                            username = post.user?.username,
                            profilePicture = post.user?.profilePicture
                        )
                    )
            }

        } catch (ex: Exception) {
            Log.e("PostRepo", ex.toString())
        }
    }

    suspend fun deleteUserPosts(userID: String) {
        postDAO.deleteAllUserPost(userID)
    }

    suspend fun deletePost(post: Post) {
        postDAO.deletePost(post)
        postAPI.deletePost(ServiceBuilder.token!!, post._id)

    }

}