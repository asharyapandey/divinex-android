package com.asharya.divinex.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asharya.divinex.entity.Notification
import com.asharya.divinex.entity.User

@Dao
interface NotificationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification)

    @Query("select * from Notification")
    suspend fun retrieveNotification() : List<Notification>
}