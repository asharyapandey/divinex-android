package com.asharya.divinex.response

import com.asharya.divinex.model.User

data class UserResponse(
    val success : Boolean? = null,
    val user: User? = null
)
