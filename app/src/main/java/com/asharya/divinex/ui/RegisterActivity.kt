package com.asharya.divinex.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.asharya.divinex.R
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.model.User
import com.asharya.divinex.repository.UserRepository
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
                    //val user = User(username, email, gender, password)
//                    addToDatabase(user)
                    //registerUser(user)
                    toLogin()
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

    private fun registerUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.registerUser(user)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity, "User Registered", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterActivity, ex.toString(), Toast.LENGTH_SHORT).show()
                }

            }

        }

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