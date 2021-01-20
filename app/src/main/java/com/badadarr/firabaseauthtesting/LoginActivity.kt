package com.badadarr.firabaseauthtesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        login()
    }

    private fun login() {
        btnLogin.setOnClickListener {

            if(TextUtils.isEmpty(edEmail_NoHP.text.toString())) {
                edEmail_NoHP.setError("Masukkan Email / No Handphone Terlebih Dahulu")
                return@setOnClickListener
            } else  if(TextUtils.isEmpty(edPassword.text.toString())) {
                edPassword.setError("Masukkan Password Terlebih Dahulu")
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(edEmail_NoHP.text.toString(), edPassword.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Login failed, please try again!", Toast.LENGTH_LONG).show()
                    }
                }
        }

        registerTxt.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}