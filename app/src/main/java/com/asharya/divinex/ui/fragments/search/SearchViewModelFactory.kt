package com.asharya.divinex.ui.fragments.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asharya.divinex.repository.PostRepository
import com.asharya.divinex.repository.UserRepository

class SearchViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return SearchViewModel(repository) as T
    }
}