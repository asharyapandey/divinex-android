package com.asharya.divinex.response

import com.asharya.divinex.model.User

data class SearchResponse(
    val success : Boolean? = null,
    val users: List<User>? = null
)
