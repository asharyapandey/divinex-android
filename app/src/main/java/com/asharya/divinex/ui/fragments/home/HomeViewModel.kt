package com.asharya.divinex.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.entity.Post
import com.asharya.divinex.repository.PostRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PostRepository): ViewModel() {

    private val _posts= MutableLiveData<List<com.asharya.divinex.entity.Post>>()
    val posts: LiveData<List<com.asharya.divinex.entity.Post>>
    get() = _posts

    fun getPosts() {
        viewModelScope.launch {
            _posts.value = repository.getPostFeed()
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            repository.deletePost(post)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}