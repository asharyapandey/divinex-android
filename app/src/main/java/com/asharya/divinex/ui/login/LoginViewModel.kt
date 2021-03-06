package com.asharya.divinex.ui.login

import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
    private val myPref : SharedPreferences
) : ViewModel() {


    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = userRepository.loginUser(username, password)
                if (response.success == true) {
                    // current user details
                    ServiceBuilder.token = response.token
                    ServiceBuilder.currentUser = response.user
                    // saving to shared pref
                    saveSharedPref(username, password)
                    // updating the ui
                    _isLoggedIn.value = true
                }
            } catch (ex: Exception) {
                Log.e("LoginViewModel", ex.toString())
                _isLoggedIn.value = false
            }
        }
    }
    private fun saveSharedPref(username: String, password: String) {
        val editor = myPref.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }

    override fun onCleared() {
        super.onCleared()
    }
}