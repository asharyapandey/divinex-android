package com.asharya.divinex.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.asharya.divinex.R
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.repository.UserRepository
import com.asharya.divinex.ui.DashboardActivity
import com.asharya.divinex.ui.login.LoginActivity
import kotlinx.coroutines.*
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val userDao = DivinexDB.getInstance(this@SplashActivity).getUserDAO()
        val repository = UserRepository(userDao)
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        viewModel = ViewModelProvider(this, SplashViewModelFactory(repository, sharedPref)).get(
            SplashViewModel::class.java)

        viewModel.login()

        viewModel.isLoggedIn.observe(this, Observer { isLoggedIn ->
            if (isLoggedIn) {
                startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        })
    }
}