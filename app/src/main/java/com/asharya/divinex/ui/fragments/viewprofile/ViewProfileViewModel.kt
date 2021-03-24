package com.asharya.divinex.ui.fragments.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.entity.Post
import com.asharya.divinex.model.User
import com.asharya.divinex.repository.PostRepository
import com.asharya.divinex.repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ViewProfileViewModel(private val userRepository: UserRepository, private val postRepository: PostRepository): ViewModel() {

    private val _user= MutableLiveData<User>()
    val user: LiveData<User>
    get() = _user

    private val _posts= MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>>
        get() = _posts

    private val _followed= MutableLiveData<Boolean>()
    val followed: LiveData<Boolean>
        get() = _followed

    private val _unfollowed= MutableLiveData<Boolean>()
    val unfollowed: LiveData<Boolean>
        get() = _unfollowed
    var userID = ""

    init {

    }

    fun getCurrentUser(id: String) {
        viewModelScope.launch {
            try {
                val response = userRepository.getUserById(id)
                if (response.success == true)
                    _user.value = response.user!!
            } catch (ex: Exception) {
                Log.i("AddPostViewModel", ex.toString())
            }
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


    fun followUser(id: String) {
        viewModelScope.launch {
            _followed.value = userRepository.followUser(id)
        }
    }

    fun unFollowUser(id: String) {
        viewModelScope.launch {
            _unfollowed.value = userRepository.unFollowUser(id)
        }
    }

    fun deletePosts(id: String) {
        viewModelScope.launch {
            postRepository.deleteUserPosts(id)
        }
    }
    override fun onCleared() {
        super.onCleared()
    }
}