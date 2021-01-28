package com.asharya.divinex.model

import androidx.room.Entity

@Entity
data class User(
    val username: String,
    val email: String,
    val gender: String,
    val password: String
)
