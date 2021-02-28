package com.asharya.divinex.ui.fragments.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.entity.UserPost
import com.asharya.divinex.model.Post
import com.asharya.divinex.model.User
import com.asharya.divinex.repository.PostRepository
import com.asharya.divinex.repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ProfileViewModel(private val userRepository: UserRepository, private val postRepository: PostRepository): ViewModel() {

    private val _user= MutableLiveData<User>()
    val user: LiveData<User>
    get() = _user

    private val _posts= MutableLiveData<List<UserPost>>()
    val posts: LiveData<List<UserPost>>
        get() = _posts
    init {
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            try {
                val response = userRepository.getCurrentUser()
                if (response.success == true)
                    _user.value = response.user!!
            } catch (ex: Exception) {
                Log.i("AddPostViewModel", ex.toString())
            }
        }
    }

    fun getCurrentUserPosts() {
        viewModelScope.launch {
            try {
                _posts.value = postRepository.getUserPosts()
            } catch (ex: Exception) {
                Log.i("AddPostViewModel", ex.toString())
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
    }
}