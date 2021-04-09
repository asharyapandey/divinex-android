package com.asharya.divinex.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.asharya.divinex.entity.User

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: User)

    @Query("select * from User")
    suspend fun retrieveUser() : User
}