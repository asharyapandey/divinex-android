package com.asharya.divinex.response

import com.asharya.divinex.model.Post

data class UpdateDeletePostResponse(
    val success: Boolean? = null,
    val posts: Post? = null
)
