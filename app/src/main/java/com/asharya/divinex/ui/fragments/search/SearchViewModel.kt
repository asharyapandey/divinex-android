package com.asharya.divinex.ui.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.entity.FeedPost
import com.asharya.divinex.model.User
import com.asharya.divinex.repository.UserRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: UserRepository): ViewModel() {

    private val _users= MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
    get() = _users

    fun getUsers(term: String) {
        viewModelScope.launch {
            _users.value = repository.getSearchedUsers(term)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}