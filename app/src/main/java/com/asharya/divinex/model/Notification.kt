package com.asharya.divinex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Notification(
    var _id : String,
    var action: String? = null,
    var user: User? = null,
)


