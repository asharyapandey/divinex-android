package com.asharya.divinex.model

data class Post(
    val _id : String,
    val caption: String? = null,
    val image: String? = null,
    val user: User? = null,
)
// TODO : Figure out how to handle likes


