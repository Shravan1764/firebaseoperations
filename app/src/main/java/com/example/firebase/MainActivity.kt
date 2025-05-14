package com.example.firebase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.AddNote.setOnClickListener {
            startActivity(Intent(this,Notes::class.java))
        }
        binding.CreateNote.setOnClickListener {
            startActivity(Intent(this,AllNotes::class.java))
        }
        }
    }

