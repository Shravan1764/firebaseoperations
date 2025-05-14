package com.example.firebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebase.databinding.ActivityNotesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Notes : AppCompatActivity() {

    private val binding : ActivityNotesBinding by lazy {
        ActivityNotesBinding.inflate(layoutInflater)
    }
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        binding.saveNote.setOnClickListener {
            val title = binding.title.text.toString()
            val description = binding.description.text.toString()

            if (title.isEmpty() || description.isEmpty()){
                Toast.makeText(this, "Either of the field is empty", Toast.LENGTH_SHORT).show()
            }
            else{
                val currentUser = auth.currentUser
                currentUser?.let { user->
                    // generate unique key for the note
                    val notekey = databaseReference.child("users").child(user.uid).child("notes ").push().key

                    // note item refrence

                    val noteitem = Dataitems(title,description,notekey?:"")
                    if (notekey != null) {
                        // add notes to the user note
                        databaseReference.child("users").child(user.uid).child("notes")
                            .child(notekey).setValue(noteitem)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
            }

        }
    }
}