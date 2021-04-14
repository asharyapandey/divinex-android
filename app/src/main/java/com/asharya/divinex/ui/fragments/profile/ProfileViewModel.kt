package com.asharya.divinex.ui.fragments.profile

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.entity.Post
import com.asharya.divinex.entity.User
import com.asharya.divinex.repository.PostRepository
import com.asharya.divinex.repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val myPreferences: SharedPreferences
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>>
        get() = _posts

    private val _isLoggedOut = MutableLiveData<Boolean>()
    val isLoggedOut: LiveData<Boolean>
        get() = _isLoggedOut

    fun getCurrentUser() {
        viewModelScope.launch {
            val user= userRepository.getCurrentUser()
            _user.value = user
        }
    }

    fun getCurrentUserPosts(id: String) {
        viewModelScope.launch {
            try {
                _posts.value = postRepository.getUserPosts(id)
            } catch (ex: Exception) {
                Log.i("AddPostViewModel", ex.toString())
            }
        }
    }

    fun logout() {
        // delete everything
        viewModelScope.launch {
            try {
                postRepository.deleteAllPosts()
                userRepository.deleteAllUser()
                ServiceBuilder.token = null
                ServiceBuilder.currentUser = null
                deleteSharedPref()
                _isLoggedOut.value = true
            } catch (ex: Exception) {
                Log.e("ProfileViewModel", ex.toString())
                _isLoggedOut.value = false
            }
        }
    }

    private fun deleteSharedPref() {
        val editor = myPreferences.edit()
        editor.putString("username", "")
        editor.putString("password", "")
        editor.apply()
    }
    override fun onCleared() {
        super.onCleared()
    }
}