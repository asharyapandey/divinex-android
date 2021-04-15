package com.asharya.divinex.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.system.measureTimeMillis

@Entity
data class Notification(
    @PrimaryKey
    var _id: String,
    var username: String? = null,
    var userID: String? = null,
    var profilePicture: String? = null,
    var action: String? = null,
)