package com.asharya.divinex.ui.splash

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.model.User
import com.asharya.divinex.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class SplashViewModel(
    private val userRepository: UserRepository,
    private val myPref: SharedPreferences
) : ViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    private val _hasSharedPref = MutableLiveData<Boolean>()
    val hasSharedPref: LiveData<Boolean>
        get() = _hasSharedPref


    fun login() {
        viewModelScope.launch {
            try {
                val username = myPref.getString("username", "")
                val password = myPref.getString("password", "")
                if (username == "" || password == "") {
                    delay(500)
                    _isLoggedIn.value = false
                } else {
                    val response = userRepository.loginUser(username!!, password!!)
                    if (response.success == true) {
                        // current user details
                        ServiceBuilder.token = response.token
                        ServiceBuilder.currentUser = response.user
                        // saving to shared pref
                        saveSharedPref(username, password, response.user?._id!!)
                        // updating the ui
                        _isLoggedIn.value = true
                    }
                }
            } catch (ex: Exception) {
                Log.e("LoginViewModel", ex.toString())
                _isLoggedIn.value = false
            }
        }
    }

    fun checkSharedPref() {
        val username = myPref.getString("username", "")
        val password = myPref.getString("password", "")
        val id = myPref.getString("id", "")
        if (username == "" || password == "" || id == "") {
        } else {
            ServiceBuilder.currentUser =
                User(_id = id, email = "test@test.com", gender = "Male", username = "username")
            _hasSharedPref.value = true
        }
    }

    private fun saveSharedPref(username: String, password: String, id: String) {
        val editor = myPref.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.putString("id", id)
        editor.apply()
    }

    override fun onCleared() {
        super.onCleared()
    }
}