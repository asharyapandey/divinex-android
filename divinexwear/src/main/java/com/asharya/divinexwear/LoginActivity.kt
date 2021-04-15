package com.asharya.divinexwear

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : WearableActivity() {
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Enables Always-on
        setAmbientEnabled()

        etUsername = findViewById(R.id.etUsername)
        etPassword= findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password= etPassword.text.toString().trim()

            Toast.makeText(this, "$username, $password", Toast.LENGTH_SHORT).show()
        }
    }

    fun Login(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val response =

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, ex.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}