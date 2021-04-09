package com.asharya.divinex.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.entity.Post
import com.asharya.divinex.entity.User
import com.asharya.divinex.repository.PostRepository
import com.asharya.divinex.repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {


    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    suspend fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = userRepository.loginUser(username, password)
                if (response.success == true) {
                    ServiceBuilder.token = response.token
                    ServiceBuilder.currentUser = response.user
                    _isLoggedIn.value = true
                }
            } catch (ex: Exception) {
                Log.e("LoginViewModel", ex.toString())
                _isLoggedIn.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}