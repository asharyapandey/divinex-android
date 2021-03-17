package com.asharya.divinex.response

import com.asharya.divinex.model.Comment

data class AddCommentResponse(
    val success: Boolean? = null,
    val comment: Comment? = null
)
