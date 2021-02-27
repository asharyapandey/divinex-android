package com.asharya.divinex.ui.fragments.home

import android.util.Log
import android.webkit.MimeTypeMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.model.Post
import com.asharya.divinex.repository.PostRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception

class HomeViewModel(private val repository: PostRepository): ViewModel() {

    private val _posts= MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>>
    get() = _posts
    init {
    }

    fun getPosts() {
        viewModelScope.launch {
            try {
                val response = repository.getPostFeed()
                if (response.success == true)
                    _posts.value = response.posts!!
            } catch (ex: Exception) {
                Log.i("AddPostViewModel", ex.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}