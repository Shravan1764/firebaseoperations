package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth


class signup_activity : AppCompatActivity() {

    private val binding : ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    private lateinit var auth : FirebaseAuth
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(binding.root)

                // initialize firebase auth
                auth = FirebaseAuth.getInstance()

                binding.signup.setOnClickListener {
                    startActivity(Intent(this,loginActivity::class.java))
                    finish()
                }

                binding.register.setOnClickListener {
                    val email = binding.email.text.toString()
                    val username = binding.username.text.toString()
                    val password = binding.rpassword.text.toString()
                    val rpassword = binding.rpassword.text.toString()

                    if (email.isEmpty() || username.isEmpty() || password.isEmpty() || rpassword.isEmpty()){
                        Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                    }
                    else if(password != rpassword){
                        Toast.makeText(this,"password and repeat password donot match", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        auth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(this) {task->
                                if (task.isSuccessful){
                                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this,loginActivity::class.java))
                                    finish()
                                }
                                else{
                                    Toast.makeText(this, "Registration failed ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
            }
        }
