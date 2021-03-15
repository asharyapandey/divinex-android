package com.asharya.divinex.response

import com.asharya.divinex.model.User

data class LoginResponse(
    val success : Boolean? = null,
    val token : String? = null,
    val user: User? = null
)
