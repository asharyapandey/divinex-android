package com.asharya.divinex.response

import com.asharya.divinex.model.Comment

data class CommentResponse(
    val success: Boolean? = null,
    val comments: List<Comment>? = null
)
