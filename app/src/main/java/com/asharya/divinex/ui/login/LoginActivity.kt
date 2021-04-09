package com.asharya.divinex.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.asharya.divinex.R
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.repository.UserRepository
import com.asharya.divinex.ui.DashboardActivity
import com.asharya.divinex.ui.register.RegisterActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private lateinit var tvRegister: TextView
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var clLogin: ConstraintLayout
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)
        clLogin = findViewById(R.id.clLogin)

        val userDao = DivinexDB.getInstance(this@LoginActivity).getUserDAO()
        val repository = UserRepository(userDao)
        viewModel = ViewModelProvider(this, LoginViewModelFactory(repository)).get(LoginViewModel::class.java)



        btnLogin.setOnClickListener {
            if (validate()) {
                val username = etUsername.text.toString().trim()
                val password = etPassword.text.toString().trim()
                loginAPI(username, password)
            }
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }


    private fun loginAPI(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userDao = DivinexDB.getInstance(this@LoginActivity).getUserDAO()
                val repository = UserRepository(userDao)
                val response = repository.loginUser(username, password)
                if (response.success == true) {
                    saveSharedPref(username, password)
                    ServiceBuilder.token = response.token
                    ServiceBuilder.currentUser = response.user
                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        val snackbar = Snackbar.make(clLogin, "Invalid Credentials", Snackbar.LENGTH_LONG)
                        snackbar.setAction("OK", View.OnClickListener {
                            snackbar.dismiss()
                        })
                        snackbar.show()
                    }

                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@LoginActivity,
                        ex.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun validate(): Boolean {
        when {
            TextUtils.isEmpty(etUsername.text) -> {
                etUsername.error = "Please Enter Your Username"
                etUsername.requestFocus()
                return false
            }
            TextUtils.isEmpty(etPassword.text) -> {
                etPassword.error = "Please Enter Your Username"
                etPassword.requestFocus()
                return false
            }
        }
        return true
    }

    private fun saveSharedPref(username: String, password: String) {
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }
}