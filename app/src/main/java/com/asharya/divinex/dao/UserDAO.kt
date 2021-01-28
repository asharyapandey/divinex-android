package com.asharya.divinex.dao

import androidx.room.Insert
import com.asharya.divinex.model.User

interface UserDAO {

    @Insert
    suspend fun insertUser(user: User)
}