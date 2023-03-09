package com.example.chatx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edtEmail = findViewById(R.id.user_email)
        edtPassword = findViewById(R.id.user_pass)
        btnLogin = findViewById(R.id.btn_login)
        btnSignup = findViewById(R.id.btn_signup)

        btnSignup.setOnClickListener{
           val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email,password)
        }

    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent =Intent(this@LoginActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                    Toast.makeText(this, "Login SuccessFully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid Details!", Toast.LENGTH_SHORT).show()

                }
            }
    }
}