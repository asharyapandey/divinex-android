package com.asharya.divinex.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.asharya.divinex.utils.TimeStampConverter
import java.time.OffsetDateTime
import java.util.*

@Entity
data class Post(
    @PrimaryKey
    var _id: String,
    var caption: String? = null,
    var image: String? = null,
    var userID: String? = null,
    var username: String? = null,
    var profilePicture: String? = null,
    @TypeConverters(TimeStampConverter::class)
    var createdAt: OffsetDateTime? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("createdAt")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(caption)
        parcel.writeString(image)
        parcel.writeString(userID)
        parcel.writeString(username)
        parcel.writeString(profilePicture)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}
