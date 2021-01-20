package com.badadarr.firabaseauthtesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        loadProfile()

        updateButton.setOnClickListener {
            startActivity(Intent(this, EditActivity::class.java))
        }

    }

    private fun loadProfile() {

        val user = auth.currentUser
        val userRefrence = databaseReference?.child(user?.uid!!)

        emailText.text = "Email: " +user?.email

        userRefrence?.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                firstnameText.text = "First Name: " +snapshot.child("firstname").value.toString()

                lastnameText.text = "Last Name: " +snapshot.child("lastname").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO()
            }

        })

        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}