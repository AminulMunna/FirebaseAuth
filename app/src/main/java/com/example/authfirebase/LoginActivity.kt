package com.example.authfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authfirebase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Going to another page
        binding.loginPageRegBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
        mAuth = FirebaseAuth.getInstance()

        //LoginProcess
        binding.loginBTN.setOnClickListener {
            val loginName = binding.enterUsername.text.toString()
            val loginPass = binding.enterPass.text.toString()

            signInWithEmailPass(loginName, loginPass)
        }
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {

            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun signInWithEmailPass(loginName: String, loginPass: String) {

        mAuth.signInWithEmailAndPassword(loginName, loginPass).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}