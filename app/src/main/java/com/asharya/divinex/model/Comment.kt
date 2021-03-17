package com.asharya.divinex.model

data class Comment(
    val _id: String? = null,
    val comment: String? = null,
    val commentedBy: User? = null,
    val post: String? = null,
    val commentedAt: String? = null
)
