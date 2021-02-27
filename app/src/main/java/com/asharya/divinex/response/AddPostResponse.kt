package com.asharya.divinex.response

import com.asharya.divinex.model.Post

data class AddPostResponse(
    val success: Boolean? = null,
    val post: Post? = null
)
