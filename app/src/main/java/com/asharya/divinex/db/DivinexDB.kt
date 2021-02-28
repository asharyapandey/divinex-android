package com.asharya.divinex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.asharya.divinex.dao.PostDAO
import com.asharya.divinex.dao.UserDAO
import com.asharya.divinex.entity.FeedPost
import com.asharya.divinex.model.User

@Database(
    entities = [(User::class), (FeedPost::class)],
    version = 1
)
abstract class DivinexDB : RoomDatabase() {

    abstract fun getUserDAO() : UserDAO
    abstract fun getPostDAO() : PostDAO

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