package com.asharya.divinex.ui.fragments.userposts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.repository.PostRepository
import kotlinx.coroutines.launch

class UserPostsViewModel(private val repository: PostRepository): ViewModel() {

    private val _posts= MutableLiveData<List<com.asharya.divinex.entity.Post>>()
    val posts: LiveData<List<com.asharya.divinex.entity.Post>>
    get() = _posts

    fun getPosts(id: String) {
        viewModelScope.launch {
            _posts.value = repository.getUserPosts(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}