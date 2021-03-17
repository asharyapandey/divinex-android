package com.asharya.divinex.dao

import androidx.room.*
import com.asharya.divinex.entity.Comment
import com.asharya.divinex.entity.Post
import com.asharya.divinex.entity.UserPost

@Dao
interface CommentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addComment(comment: Comment)

    @Query("select * from Comment where post=:id")
    suspend fun getComments(id: String): List<Comment>

    @Delete
    suspend fun deleteComment(comment: Comment)
}