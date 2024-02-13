package com.example.authfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authfirebase.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Going to another page
        binding.rgPageLogin.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        //registerValidation

        binding.registerBTN.setOnClickListener {
            val email = binding.Email.text.toString()
            val password = binding.pass.text.toString()
            val confirmPasseord = binding.conPass.text.toString()

            if (email.isEmpty() || password.isEmpty() || confirmPasseord.isEmpty()) {
                Toast.makeText(this, "Please enter all fields properly", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPasseord) {
                Toast.makeText(
                    this,
                    "Password and confirm password should be same",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (password.length < 8) {
                Toast.makeText(this, "Password should be at least 8 character", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Firebase.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {

                        if (it.isSuccessful) {
                            Toast.makeText(this, "Registration succesfull", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "registration failed", Toast.LENGTH_SHORT).show()
                        }

                    }
            }
        }


    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser != null) {

            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}