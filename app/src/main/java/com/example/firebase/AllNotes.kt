package com.example.firebase

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.databinding.ActivityAllNotesBinding
import com.example.firebase.databinding.UpdatedialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllNotes : AppCompatActivity(),notes_adapter.OnItemClickListener {

    private val binding: ActivityAllNotesBinding by lazy {
        ActivityAllNotesBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    lateinit var myadapter: notes_adapter
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)

        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        currentUser.let { user ->
            val noteRef = databaseReference.child("users").child(user!!.uid).child("notes")
            noteRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val notelist = mutableListOf<Dataitems>()
                    for (noteSnapshot in snapshot.children) {
                        val note = noteSnapshot.getValue(Dataitems::class.java)
                        note?.let {
                            notelist.add(it)
                        }
                        println(notelist)
                        myadapter = notes_adapter(notelist, this@AllNotes)
                        recyclerView.adapter = myadapter
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@AllNotes, "Failed to fetch notes", Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }


    }

    override fun ondeleteClick(noteid: String) {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val noteRefr = databaseReference.child("users").child(user.uid).child("notes")
            noteRefr.child(noteid).removeValue()
        }
    }

    override fun onupdateClick(noteid: String, title: String, description: String) {

        val dialogbind = UpdatedialogBinding.inflate(LayoutInflater.from(this))
        val dialog = AlertDialog.Builder(this).setView(dialogbind.root)
            .setTitle("Update Note")
            .setPositiveButton("Update") { dialog, which ->
                val updatedtitle = dialogbind.editTextText.text.toString()
                val updateddescription = dialogbind.editTextText2.text.toString()
                updateNote(noteid, updatedtitle, updateddescription)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .create()
        dialogbind.editTextText.setText(title)
        dialogbind.editTextText2.setText(description)
        dialog.show()


    }


    private fun updateNote(noteid: String, title: String, description: String) {

        val currentUser = auth.currentUser
        val noteRef = databaseReference.child("users").child(currentUser!!.uid).child("notes")
        val updatedata = Dataitems(title, description, noteid)
        noteRef.child(noteid).setValue(updatedata)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to update note", Toast.LENGTH_SHORT).show()
                }

            }
    }
}
