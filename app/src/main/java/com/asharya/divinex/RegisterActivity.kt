package com.asharya.divinex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup

class RegisterActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var rgGender : RadioGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etUsername = findViewById(R.id.etUsername)
        etPassword= findViewById(R.id.etPassword)
        etConfirmPassword= findViewById(R.id.etConfirmPassword)
        etEmail= findViewById(R.id.etEmail)
        btnRegister = findViewById(R.id.btnRegister)
        rgGender = findViewById(R.id.rgGender)

    }
}