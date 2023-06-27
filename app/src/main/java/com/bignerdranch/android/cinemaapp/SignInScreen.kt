package com.bignerdranch.android.cinemaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignInScreen : AppCompatActivity() {

    private lateinit var editemail: EditText
    private lateinit var editpassword: EditText
    private lateinit var buttonLogIn: ImageButton
    private lateinit var buttonRegistration: ImageButton
    private  lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_screen)

        editemail = findViewById(R.id.edit_email)
        editpassword = findViewById(R.id.edit_password)
        buttonLogIn = findViewById(R.id.btn_login)
        buttonRegistration = findViewById(R.id.btn_registration)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        buttonRegistration.setOnClickListener{
            val email = editemail.text.toString()
            val password = editpassword.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()){
                val editor = sharedPreferences.edit()
                editor.putString("email", email)
                editor.putString("password", password)
                editor.apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Пожалуйста, заполните поля логина и пароля", Toast.LENGTH_SHORT).show()
            }
        }
        buttonLogIn.setOnClickListener{
            val savedEmail = sharedPreferences.getString("email", "")
            val savedPassword = sharedPreferences.getString("password", "")
            val email = editemail.text.toString()
            val password = editpassword.text.toString()
            if(savedEmail.isNullOrEmpty() || savedPassword.isNullOrEmpty()){
                Toast.makeText(this, "Пожалуйста, сначала зарегестрируйтесь", Toast.LENGTH_SHORT).show()
            }
            if(email.isNotEmpty() && password.isNotEmpty()){
                if(email == savedEmail && password == savedPassword){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            else{
                Toast.makeText(this, "Пожалуйста, заполните поля логина и пароля", Toast.LENGTH_SHORT).show()
            }
        }
    }
}