package com.asharya.divinex.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.asharya.divinex.R
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.model.User
import com.asharya.divinex.repository.UserRepository
import com.asharya.divinex.ui.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var rgGender: RadioGroup
    private lateinit var rdoOthers: RadioButton
    private lateinit var tvLogin: TextView

    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        etEmail = findViewById(R.id.etEmail)
        btnRegister = findViewById(R.id.btnRegister)
        rgGender = findViewById(R.id.rgGender)
        rdoOthers = findViewById(R.id.rdoOthers)
        tvLogin = findViewById(R.id.tvLogin)


        val userDao = DivinexDB.getInstance(this@RegisterActivity).getUserDAO()
        val repository = UserRepository(userDao)
        viewModel = ViewModelProvider(
            this,
            RegisterViewModelFactory(repository)
        ).get(RegisterViewModel::class.java)

        viewModel.isRegistered.observe(this, Observer { isRegistered ->
            if (isRegistered) {
                Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT)
                    .show()
                toLogin()
            } else {
                Toast.makeText(this, "Something Went Wrong Please try Again!!", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        btnRegister.setOnClickListener {
            if (validate()) {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                val confirmPassword = etConfirmPassword.text.toString()
                val email = etEmail.text.toString()
                val rdoID = rgGender.checkedRadioButtonId
                val checkedRadioButton: RadioButton = findViewById(rdoID)
                val gender = checkedRadioButton.text.toString()

                if (password == confirmPassword) {
                    viewModel.registerUser(username, password, email, gender)
                } else {
                    etConfirmPassword.error = "Passwords do not match"
                    etConfirmPassword.requestFocus()
                }
            }
        }

        tvLogin.setOnClickListener {
            toLogin()
        }
    }

    private fun toLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun validate(): Boolean {
        when {
            TextUtils.isEmpty(etUsername.text) -> {
                etUsername.error = "Please enter a Username"
                etUsername.requestFocus()
                return false
            }
            TextUtils.isEmpty(etEmail.text) -> {
                etEmail.error = "Please enter a Email"
                etEmail.requestFocus()
                return false
            }
            (rgGender.checkedRadioButtonId <= 0) -> {
                Toast.makeText(this, "Please select a Gender", Toast.LENGTH_SHORT).show()
                rdoOthers.requestFocus()
                return false
            }
            TextUtils.isEmpty(etPassword.text) -> {
                etPassword.error = "Please enter a Password"
                etPassword.requestFocus()
                return false
            }
            TextUtils.isEmpty(etConfirmPassword.text) -> {
                etConfirmPassword.error = "Please confirm the Password"
                etConfirmPassword.requestFocus()
                return false
            }
        }
        return true
    }
}