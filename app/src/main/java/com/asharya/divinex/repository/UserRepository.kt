package com.asharya.divinex.repository

import android.util.Log
import com.asharya.divinex.api.ApiRequest
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.api.UserAPI
import com.asharya.divinex.model.User
import com.asharya.divinex.response.LoginResponse
import com.asharya.divinex.response.SearchResponse
import com.asharya.divinex.response.UserResponse
import java.lang.Exception

class UserRepository : ApiRequest() {
    // retrofit instance of userAPI
    private val userApi = ServiceBuilder.buildService(UserAPI::class.java)

    // register user
    suspend fun registerUser(user: User): LoginResponse {
        return apiRequest {
            userApi.registerUser(user)
        }
    }

    suspend fun loginUser(username: String, password: String): LoginResponse {
        return apiRequest {
            userApi.loginUser(username, password)
        }
    }

    suspend fun getCurrentUser(): UserResponse {
        return apiRequest {
            userApi.getCurrentUser(ServiceBuilder.token!!)
        }
    }

    suspend fun getUserById(id: String): UserResponse {
        return apiRequest {
            userApi.getUserById(ServiceBuilder.token!!, id)
        }
    }

    suspend fun getSearchedUsers(term: String): List<User> {
        try {
            val response = apiRequest {
                userApi.getSearchUsers(ServiceBuilder.token!!, term)
            }
            if (response.success == true) return response.users!!
        } catch (ex: Exception) {
            Log.d("UserRepo", ex.toString())
        }
        return emptyList()
    }

    suspend fun followUser(id: String) : Boolean {
        try {
            val response = apiRequest {
                userApi.followUser(ServiceBuilder.token!!, id)
            }
            if (response.success == true) return response.success!!
            return false
        } catch (ex: Exception) {
            return false
            Log.d("UserRepo", ex.toString())
        }
    }

    suspend fun unFollowUser(id: String): Boolean {
        try {
            val response = apiRequest {
                userApi.unFollowUser(ServiceBuilder.token!!, id)
            }
            if (response.success == true) return response.success!!
            return false
        } catch (ex: Exception) {
            return false
            Log.d("UserRepo", ex.toString())
        }
    }

}