package com.asharya.divinex.dao

import androidx.room.*
import com.asharya.divinex.entity.FeedPost

@Dao
interface PostDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPost(post: FeedPost)

    @Query("select * from FeedPost")
    suspend fun getAllPosts(): List<FeedPost>

    @Query("select * from FeedPost where _id=:id")
    suspend fun getPost(id: String) :FeedPost

    @Update
    suspend fun updatePost(post: FeedPost)
}