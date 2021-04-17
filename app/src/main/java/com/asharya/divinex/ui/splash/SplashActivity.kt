package com.asharya.divinex.ui.splash

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
            SplashViewModel::class.java
        )

        if (isOnline(this)) {
            viewModel.login()
        } else {
            viewModel.checkSharedPref()
        }

        // direct to login screeen if doesn't have share pref and there is no internet
        viewModel.hasSharedPref.observe(this, Observer { hasSharedPref ->
            if (hasSharedPref) {
                startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        })



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

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}