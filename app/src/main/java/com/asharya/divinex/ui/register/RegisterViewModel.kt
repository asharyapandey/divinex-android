package com.asharya.divinex.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.model.User
import com.asharya.divinex.repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class RegisterViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private var _isRegistered = MutableLiveData<Boolean>()
    val isRegistered : LiveData<Boolean>
    get() = _isRegistered

    fun registerUser(username: String, password: String, email: String, gender: String) {
        val user = User(username = username, password = password, email = email, gender = gender)
        try {
            viewModelScope.launch {
                val response = userRepository.registerUser(user)
                if (response.success == true) {
                    _isRegistered.value = true
                }
            }

        } catch(ex: Exception) {
            Log.e("RegisterViewModel", ex.toString())
            _isRegistered.value = false
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}