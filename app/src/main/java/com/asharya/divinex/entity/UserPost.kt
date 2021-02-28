package com.asharya.divinex.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserPost(
    @PrimaryKey
    var _id : String,
    var caption: String? = null,
    var image: String? = null,
    var userID: String? = null,
    var username: String? = null,
    var profilePicture: String? = null,
)
