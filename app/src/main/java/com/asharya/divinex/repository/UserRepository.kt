package com.asharya.divinex.repository

import com.asharya.divinex.api.ApiRequest
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.api.UserAPI
import com.asharya.divinex.model.User
import com.asharya.divinex.response.LoginResponse

class UserRepository: ApiRequest() {
    // retrofit instance of userAPI
    private val userApi = ServiceBuilder.buildService(UserAPI::class.java)

    // register user
    suspend fun registerUser(user: User) : LoginResponse {
        return apiRequest {
            userApi.registerUser(user)
        }
    }

    suspend fun loginUser(username: String, password: String) : LoginResponse {
        return apiRequest {
            userApi.loginUser(username, password)
        }
    }

}