package com.asharya.divinex.api

import com.asharya.divinex.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {

    // Registering User
    @POST("api/user")
    suspend fun registerUser(
        @Body user: User
    )

}