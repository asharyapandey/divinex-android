package com.asharya.divinex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var rgGender : RadioGroup
    private lateinit var rdoOthers : RadioButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etUsername = findViewById(R.id.etUsername)
        etPassword= findViewById(R.id.etPassword)
        etConfirmPassword= findViewById(R.id.etConfirmPassword)
        etEmail= findViewById(R.id.etEmail)
        btnRegister = findViewById(R.id.btnRegister)
        rgGender = findViewById(R.id.rgGender)
        rdoOthers = findViewById(R.id.rdoOthers)

        btnRegister.setOnClickListener {
            if (validate()) {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                val confirmPassword = etConfirmPassword.text.toString()
                val email =  etEmail.text.toString()
                val r = rgGender.checkedRadioButtonId
                Toast.makeText(this, "$r", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun validate() : Boolean {
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