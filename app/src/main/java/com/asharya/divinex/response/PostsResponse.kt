package com.asharya.divinex.response

import com.asharya.divinex.model.Post

data class PostsResponse(
    val success: Boolean? = null,
    val posts: List<Post>? = null
)
