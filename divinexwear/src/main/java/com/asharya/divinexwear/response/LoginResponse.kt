package com.asharya.divinexwear.response

import com.asharya.divinexwear.model.User

data class LoginResponse(
    val success : Boolean? = null,
    val token : String? = null,
    val user: User? = null
)
