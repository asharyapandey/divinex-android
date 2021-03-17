package com.asharya.divinex.ui.fragments.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asharya.divinex.repository.CommentRepository
import com.asharya.divinex.repository.PostRepository

class CommentViewModelFactory(private val repository: CommentRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return CommentViewModel(repository) as T
    }
}