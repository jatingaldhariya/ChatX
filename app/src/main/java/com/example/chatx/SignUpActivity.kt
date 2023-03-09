package com.example.chatx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {


    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button
    private lateinit var auth:FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        auth = Firebase.auth
        edtName = findViewById(R.id.user_name)
        edtEmail = findViewById(R.id.user_email)
        edtPassword = findViewById(R.id.user_pass)
        btnLogin = findViewById(R.id.btn_login)
        btnSignup = findViewById(R.id.btn_signup)


        btnSignup.setOnClickListener{
            val uemail = edtEmail.text.toString()
            val upassword = edtPassword.text.toString()
            val name = edtName.text.toString()
            
            signup(uemail,upassword,name)
            
        }

        btnLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }


    private fun signup(email: String, password: String ,name: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, email, auth.currentUser?.uid!!)
                    val intent =Intent(this@SignUpActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                    Toast.makeText(this, "Account Created! $name", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUpActivity, "Something Went Wrong,Please Try Again", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}