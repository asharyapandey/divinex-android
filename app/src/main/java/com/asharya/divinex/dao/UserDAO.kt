package com.asharya.divinex.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asharya.divinex.entity.User

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("select * from User")
    suspend fun retrieveUser() : User

    @Query("delete from User")
    suspend fun deleteAllUser()
}