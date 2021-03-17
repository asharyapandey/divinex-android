package com.asharya.divinex.ui.fragments.comments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.entity.Comment
import com.asharya.divinex.repository.CommentRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class CommentViewModel(private val repository: CommentRepository): ViewModel() {

    private val _comments= MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>>
    get() = _comments

    private val _commentAdded = MutableLiveData<Boolean>()
    val commentAdded: LiveData<Boolean>
    get() = _commentAdded

    fun getPosts(id: String) {
        viewModelScope.launch {
            _comments.value = repository.getComments(id)
        }
    }

    fun addComment(postID: String, comment: String) {
        viewModelScope.launch {
            try {
                val response = repository.addComment(id = postID, comment = comment)
                if (response.success == true) {
                    _commentAdded.value = true
                }

            } catch (ex: Exception) {
                _commentAdded.value = false
                Log.d("CommentViewModel", ex.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}