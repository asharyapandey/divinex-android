package com.asharya.divinex.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.asharya.divinex.R
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.repository.UserRepository
import kotlinx.coroutines.*
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.IO).launch {
            loginAPI()
        }
    }

    private suspend fun loginAPI() {
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val username = sharedPref.getString("username", "")
        val password = sharedPref.getString("password", "")
        if (username == "" || password == "") {
            delay(500)
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            return
        }
        withContext(Dispatchers.IO) {
            try {
                val repository = UserRepository()
                val response = repository.loginUser(username!!, password!!)
                if (response.success == true) {
                    ServiceBuilder.token = response.token
                    ServiceBuilder.currentUser = response.user
                    startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@SplashActivity,
                        ex.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}