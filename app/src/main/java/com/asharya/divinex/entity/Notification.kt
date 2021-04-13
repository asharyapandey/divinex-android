package com.asharya.divinex.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.system.measureTimeMillis

@Entity
data class Notification(
    @PrimaryKey
    var _id : String,
    var username: String,
    var userID: String,
    var profilePicture: String,
    var action: String? =null,
)