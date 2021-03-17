package com.asharya.divinex.repository

import android.util.Log
import com.asharya.divinex.api.ApiRequest
import com.asharya.divinex.api.PostAPI
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.dao.CommentDAO
import com.asharya.divinex.entity.Comment
import com.asharya.divinex.entity.Post
import com.asharya.divinex.response.AddCommentResponse
import java.lang.Exception

class CommentRepository(private val commentDAO: CommentDAO) : ApiRequest() {
    private val postAPI = ServiceBuilder.buildService(PostAPI::class.java)

    // add comment
    suspend fun addComment(comment: String, id: String): AddCommentResponse{
        return apiRequest {
            postAPI.addComment(ServiceBuilder.token!!,id, comment)
        }
    }

    // update post
//    suspend fun updatePost(): UpdateDeletePostResponse{
//    }

    suspend fun getComments(id: String): List<Comment> {
        refreshComments(id)
        return commentDAO.getComments(id)
    }

    private suspend fun refreshComments(id: String) {
        try {
            val response = apiRequest {
                postAPI.getComments(ServiceBuilder.token!!,id)
            }
            if (response.success == true) {
                for (comment in response.comments!!)
                    commentDAO.addComment(
                        Comment(
                            _id = comment._id!!,
                            comment= comment.comment,
                            post = comment.post,
                            userID = comment.commentedBy?._id,
                            username = comment.commentedBy?.username,
                            profilePicture = comment.commentedBy?.profilePicture,
                            commentedAt = comment.commentedAt
                        )
                    )
            }

        } catch (ex: Exception) {
            Log.e("CommentRepo", ex.toString())
        }
    }
//    suspend fun deletePost(post: Post) {
//        try {
//            val response = apiRequest {
//                postAPI.deletePost(ServiceBuilder.token!!, post._id)
//            }
//            if (response.success == true) {
//                postDAO.deletePost(post)
//            }
//        } catch (ex: Exception) {
//            Log.e("PostRepo", ex.toString())
//        }
//    }


}