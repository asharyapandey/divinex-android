package com.asharya.divinex.dao

import androidx.room.*
import com.asharya.divinex.entity.FeedPost
import com.asharya.divinex.entity.UserPost

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserPost(post: UserPost)

    @Query("select * from UserPost")
    suspend fun getAllUserPosts(): List<UserPost>

    @Query("select * from UserPost where _id=:id")
    suspend fun getUserPost(id: String) : UserPost

    @Update
    suspend fun updateUserPost(post: UserPost)
}