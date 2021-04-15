package com.asharya.divinex.repository

import android.util.Log
import com.asharya.divinex.api.ApiRequest
import com.asharya.divinex.api.PostAPI
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.dao.PostDAO
import com.asharya.divinex.entity.Post
import com.asharya.divinex.response.AddPostResponse
import com.asharya.divinex.response.UpdateDeletePostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.*

class PostRepository(private val postDAO: PostDAO) : ApiRequest() {
    private val postAPI = ServiceBuilder.buildService(PostAPI::class.java)

    // add post
    suspend fun addPost(caption: RequestBody, image: MultipartBody.Part): AddPostResponse {
        return apiRequest {
            postAPI.addPost(ServiceBuilder.token!!, caption, image)
        }
    }

    // update post
    suspend fun updatePost(
        caption: RequestBody,
        image: MultipartBody.Part,
        postID: String
    ): UpdateDeletePostResponse {
        return apiRequest {
            postAPI.updatePost(ServiceBuilder.token!!, postID, caption, image)
        }
    }

    suspend fun getPostFeed(): List<Post> {
        return try {
            refreshPosts()
            postDAO.getAllPosts(ServiceBuilder.currentUser?._id!!)
        } catch (ex: Exception) {
            Log.e("PostRepo", ex.toString())
            postDAO.getAllPosts(ServiceBuilder.currentUser?._id!!)
        }
    }

    private suspend fun refreshPosts() {
        val response = apiRequest {
            postAPI.getPostFeed(ServiceBuilder.token!!)
        }
        if (response.success == true) {
            for (post in response.posts!!) {
                val date = OffsetDateTime.parse(post.createdAt)
                postDAO.addPost(
                    Post(
                        _id = post._id,
                        caption = post.caption,
                        image = post.image,
                        userID = post.user?._id,
                        username = post.user?.username,
                        profilePicture = post.user?.profilePicture,
                        createdAt = date
                    )
                )
            }
        }
    }

    suspend fun getUserPosts(userID: String): List<Post> {
        return try {
            refreshUserPosts(userID)
            postDAO.getAllUserPost(userID)
        } catch (ex: Exception) {
            Log.e("PostRepo", ex.toString())
            postDAO.getAllUserPost(userID)
        }
    }

    private suspend fun refreshUserPosts(id: String) {
        val response = apiRequest {
            postAPI.getUsersPostById(ServiceBuilder.token!!, id)
        }
        if (response.success == true) {
            for (post in response.posts!!) {
                val date = OffsetDateTime.parse(post.createdAt)
                postDAO.addPost(
                    Post(
                        _id = post._id,
                        caption = post.caption,
                        image = post.image,
                        userID = post.user?._id,
                        username = post.user?.username,
                        profilePicture = post.user?.profilePicture,
                        createdAt = date
                    )
                )
            }
        }

    }

    suspend fun deleteUserPosts(userID: String) {
        postDAO.deleteAllUserPost(userID)
    }

    suspend fun deletePost(post: Post) {
        try {
            val response = apiRequest {
                postAPI.deletePost(ServiceBuilder.token!!, post._id)
            }
            if (response.success == true) {
                postDAO.deletePost(post)
            }
        } catch (ex: Exception) {
            Log.e("PostRepo", ex.toString())
        }
    }

    suspend fun deleteAllPosts() {
        postDAO.deleteAllPost()
    }


}