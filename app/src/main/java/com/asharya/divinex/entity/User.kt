package com.asharya.divinex.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.system.measureTimeMillis

@Entity
data class User(
    @PrimaryKey
    var _id : String,
    var username: String,
    var email: String,
    var gender: String,
    var profilePicture: String? =null,
    var password: String? =  null,
    var followers: Int = 0,
    var following: Int = 0
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

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

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(_id)
        dest?.writeString(username)
        dest?.writeString(gender)
        dest?.writeString(email)
        dest?.writeString(password)
        dest?.writeString(profilePicture)
        dest?.writeInt(followers)
        dest?.writeInt(following)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

