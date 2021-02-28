package com.asharya.divinex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey
    var _id : String,
    var caption: String? = null,
    var image: String? = null,
    var user: User? = null,
)
// TODO : Figure out how to handle likes


