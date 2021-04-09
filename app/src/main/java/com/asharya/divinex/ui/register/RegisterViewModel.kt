package com.asharya.divinex.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.entity.Post
import com.asharya.divinex.entity.User
import com.asharya.divinex.repository.PostRepository
import com.asharya.divinex.repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class RegisterViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {


    override fun onCleared() {
        super.onCleared()
    }
}