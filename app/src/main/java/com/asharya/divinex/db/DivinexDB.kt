package com.asharya.divinex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.asharya.divinex.dao.CommentDAO
import com.asharya.divinex.dao.NotificationDAO
import com.asharya.divinex.dao.PostDAO
import com.asharya.divinex.dao.UserDAO
import com.asharya.divinex.entity.Comment
import com.asharya.divinex.entity.Notification
import com.asharya.divinex.entity.Post
import com.asharya.divinex.entity.User
import com.asharya.divinex.utils.TimeStampConverter

@Database(
    entities = [(Post::class), (Comment::class), (User::class), (Notification::class)],
    version = 1
)
@TypeConverters(TimeStampConverter::class)
abstract class DivinexDB : RoomDatabase() {

    abstract fun getPostDAO() : PostDAO
    abstract fun getCommentDAO() : CommentDAO
    abstract fun getUserDAO() : UserDAO
    abstract fun getNotificationDAO(): NotificationDAO

    companion object {
        @Volatile
        private var INSTANCE : DivinexDB? = null

        fun getInstance(context: Context): DivinexDB {
            var tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DivinexDB::class.java,
                    "divinex_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}