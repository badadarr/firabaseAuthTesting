package com.badadarr.firabaseauthtesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        register()
    }

    private fun register() {
        registerButton.setOnClickListener {

            if (TextUtils.isEmpty(firstNameText.text.toString())) {
                firstNameText.setError("Please enter the first name")
                return@setOnClickListener

            } else if (TextUtils.isEmpty(lastNameText.text.toString())) {
                firstNameText.setError("Please enter the last name")
                return@setOnClickListener

            } else if (TextUtils.isEmpty(emailInput.text.toString())) {
                firstNameText.setError("Please enter the user name")
                return@setOnClickListener

            } else if (TextUtils.isEmpty(passwordInput.text.toString())) {
                firstNameText.setError("Please enter the password")
                return@setOnClickListener
            }

                auth.createUserWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString())
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val currentUser = auth.currentUser
                                val currentUserDb = databaseReference?.child(currentUser?.uid!!)
                                currentUserDb?.child("firstname")?.setValue(firstNameText.text.toString())
                                currentUserDb?.child("lastname")?.setValue(firstNameText.text.toString())

                                Toast.makeText(this, "Register Success!", Toast.LENGTH_LONG).show()
                                finish()

                            } else {
                                Toast.makeText(this, "Register failed, please try again!", Toast.LENGTH_LONG).show()
                            }
                        }
            }
        }
    }