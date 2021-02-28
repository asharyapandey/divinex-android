package com.asharya.divinex.ui.fragments.profile

import android.util.Log
import android.webkit.MimeTypeMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.model.Post
import com.asharya.divinex.model.User
import com.asharya.divinex.repository.PostRepository
import com.asharya.divinex.repository.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception

class ProfileViewModel(private val repository: UserRepository): ViewModel() {

    private val _user= MutableLiveData<User>()
    val user: LiveData<User>
    get() = _user
    init {
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            try {
                val response = repository.getCurrentUser()
                if (response.success == true)
                    _user.value = response.user!!
            } catch (ex: Exception) {
                Log.i("AddPostViewModel", ex.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}