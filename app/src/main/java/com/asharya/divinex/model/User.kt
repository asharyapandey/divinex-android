package com.asharya.divinex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey()
    var _id : String,
    var username: String,
    var email: String,
    var gender: String,
    var profilePicture: String,
    var password: String
)

