package com.asharya.divinex.dao

import androidx.room.Insert
import androidx.room.Query
import com.asharya.divinex.model.User

interface UserDAO {

    @Insert
    suspend fun insertUser(user: User)


    @Query("select * from User where username=(:username) and password=(:password)")
    suspend fun retrieveUser(username: String, password: String) : User
}