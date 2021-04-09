package com.asharya.divinex.dao

import androidx.room.*
import com.asharya.divinex.entity.Post

@Dao
interface PostDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPost(post: Post)

    @Query("select * from Post where userID!=:id ORDER BY datetime(createdAt) DESC")
    suspend fun getAllPosts(id: String): List<Post>

    @Query("select * from Post where _id=:id")
    suspend fun getPost(id: String) :Post

    @Update
    suspend fun updatePost(post: Post)

    @Query("select * from Post where userID=:id")
    suspend fun getAllUserPost(id: String) : List<Post>

    @Query("delete from Post where userID=:id")
    suspend fun deleteAllUserPost(id: String)

    @Delete
    suspend fun deletePost(post: Post)
}