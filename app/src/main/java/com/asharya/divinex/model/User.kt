package com.asharya.divinex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class User(
    var _id : String? = null,
    var username: String,
    var email: String,
    var gender: String,
    var profilePicture: String? =null,
    var password: String? =  null,
    var followers: List<User>? = null,
    var following: List<User>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as User
        if (_id != other._id) return false
        if (username != other.username) return false
        if (email!= other.email) return false
        if (gender!= other.gender) return false
        if (profilePicture!= other.profilePicture) return false
        return true
    }
}

