package com.asharya.divinex.api

import com.asharya.divinexwear.response.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {
    // Login
    @FormUrlEncoded
    @POST("user/login")
    suspend fun loginUser(
        @Field("username") username : String,
        @Field("password") password : String
    ) : Response<LoginResponse>

}