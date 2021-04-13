package com.asharya.divinex.response

import com.asharya.divinex.model.Notification
import com.asharya.divinex.model.User

data class NotificationResponse(
    val success : Boolean? = null,
    val notifications: List<Notification>? = null
)
