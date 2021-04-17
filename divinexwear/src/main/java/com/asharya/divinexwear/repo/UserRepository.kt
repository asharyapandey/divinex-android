package com.asharya.divinexwear.repo

import com.asharya.divinex.api.ApiRequest
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.api.UserAPI
import com.asharya.divinexwear.response.LoginResponse

class UserRepository() : ApiRequest() {
    // retrofit instance of userAPI
    private val userApi = ServiceBuilder.buildService(UserAPI::class.java)

    suspend fun loginUser(username: String, password: String): LoginResponse {
        return apiRequest {
            userApi.loginUser(username, password)
        }
    }
}