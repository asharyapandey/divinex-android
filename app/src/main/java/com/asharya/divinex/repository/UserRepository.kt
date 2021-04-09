package com.asharya.divinex.repository

import android.util.Log
import com.asharya.divinex.api.ApiRequest
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.api.UserAPI
import com.asharya.divinex.dao.UserDAO
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.model.User
import com.asharya.divinex.response.LoginResponse
import com.asharya.divinex.response.SearchResponse
import com.asharya.divinex.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception

class UserRepository(private val userDAO: UserDAO) : ApiRequest() {
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

    suspend fun getCurrentUser(): com.asharya.divinex.entity.User {
        // save in room
        return try {
            addCurrentUser()
            userDAO.retrieveUser()
        } catch (ex: Exception) {
            Log.e("UserRepo", ex.toString())
            userDAO.retrieveUser()
        }
    }

    private suspend fun addCurrentUser() {
        val response = apiRequest {
            userApi.getCurrentUser(ServiceBuilder.token!!)
        }
        if (response.success == true) {
            val user = response.user?.followers?.let {
                response.user.following?.let { it1 ->
                    response.user?._id?.let { it2 ->
                        com.asharya.divinex.entity.User(
                            _id = it2,
                            username = response.user.username,
                            gender = response.user.gender,
                            profilePicture = response.user.profilePicture,
                            email = response.user.email,
                            followers = it.size,
                            following = it1.size,
                            password = response.user.password
                        )
                    }
                }
            }
            if (user != null) {
                userDAO.insertUser(user)
            }
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

    suspend fun followUser(id: String): Boolean {
        try {
            val response = apiRequest {
                userApi.followUser(ServiceBuilder.token!!, id)
            }
            if (response.success == true) {
                ServiceBuilder.currentUser = response.user
                return response.success!!
            }
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
            if (response.success == true) {
                ServiceBuilder.currentUser = response.user
                return response.success!!
            }
            return false
        } catch (ex: Exception) {
            return false
            Log.d("UserRepo", ex.toString())
        }
    }

    suspend fun updateProfileWithImage(image: MultipartBody.Part, userID: String, email: RequestBody, gender: RequestBody) : UserResponse {
        return apiRequest {
            userApi.updateProfile(ServiceBuilder.token!!, userID, email, gender, image)
        }
    }
    suspend fun updateProfile(userID: String,email: String, gender: String) : UserResponse {
        return apiRequest {
            userApi.updateProfile(ServiceBuilder.token!!, userID, email, gender)
        }
    }

}