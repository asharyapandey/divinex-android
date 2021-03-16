package com.asharya.divinex.ui.fragments.userposts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.entity.Post
import com.asharya.divinex.repository.PostRepository
import kotlinx.coroutines.launch

class UserPostsViewModel(private val repository: PostRepository): ViewModel() {

    private val _posts= MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>>
    get() = _posts

    fun getPosts(id: String) {
        viewModelScope.launch {
            _posts.value = repository.getUserPosts(id)
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