package com.asharya.divinex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.asharya.divinex.dao.PostDAO
import com.asharya.divinex.entity.Post
import com.asharya.divinex.entity.UserPost
import com.asharya.divinex.model.User

@Database(
    entities = [(Post::class)],
    version = 1
)
abstract class DivinexDB : RoomDatabase() {

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