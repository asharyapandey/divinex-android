package com.asharya.divinex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Post(
    var _id : String,
    var caption: String? = null,
    var image: String? = null,
    var user: User? = null,
    var createdAt: String? = null
)
// TODO : Figure out how to handle likes


