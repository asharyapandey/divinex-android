package com.asharya.divinex.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comment(
    @PrimaryKey
    var _id: String,
    var comment: String? = null,
    var userID: String? = null,
    var username: String? = null,
    var profilePicture: String? = null,
    val post: String? = null,
    val commentedAt: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(_id)
        dest?.writeString(profilePicture)
        dest?.writeString(comment)
        dest?.writeString(commentedAt)
        dest?.writeString(userID)
        dest?.writeString(post)
        dest?.writeString(username)
    }
}
