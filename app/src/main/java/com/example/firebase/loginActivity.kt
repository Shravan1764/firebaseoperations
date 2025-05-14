package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
class loginActivity : AppCompatActivity() {

    private val binding : ActivityLoginBinding by lazy {

        ActivityLoginBinding.inflate(layoutInflater)

    }

    private lateinit var auth : FirebaseAuth

    override fun onStart() {
        super.onStart()

        val currentUser : FirebaseUser? = auth.currentUser
        if (currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.signup.setOnClickListener {
            startActivity(Intent(this,signup_activity::class.java))
            finish()
        }

        auth = FirebaseAuth.getInstance()

        binding.login.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.Password.text.toString()

            if (username.isEmpty() || password.isEmpty()){
                binding.username.error = "Please enter username"
                binding.Password.error = "Please enter password"
            }
            else {
                    auth.signInWithEmailAndPassword(username,password)
                        .addOnCompleteListener { task->
                            if (task.isSuccessful){
                                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this,MainActivity::class.java))
                                finish()
                            }
                            else{
                                Toast.makeText(this, "Login failed ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }

                        }
            }
        }
    }
}