package com.asharya.divinex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    var username: String,
    var email: String,
    var gender: String,
    var password: String
) {
    @PrimaryKey(autoGenerate = true)
    var userId : Int = 0
}
