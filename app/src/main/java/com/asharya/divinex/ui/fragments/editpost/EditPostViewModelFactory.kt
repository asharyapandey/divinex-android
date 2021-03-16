package com.asharya.divinex.ui.fragments.editpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asharya.divinex.repository.PostRepository

class EditPostViewModelFactory(private val repository: PostRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return EditPostViewModel(repository) as T
    }
}