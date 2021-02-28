package com.asharya.divinex.dao

import androidx.room.*
import com.asharya.divinex.model.Post

@Dao
interface PostDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPost(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllPosts(vararg post: Post)

    @Query("select * from Post")
    suspend fun getAllPosts(): List<Post>

    @Query("select * from Post where _id=:id")
    suspend fun getPost(id: String) : Post

    @Update
    suspend fun updatePost(post: Post)
}