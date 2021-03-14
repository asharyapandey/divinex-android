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

