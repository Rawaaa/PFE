package com.example.aveuglesourd


import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.util.Patterns
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


import kotlinx.android.synthetic.main.activity_authentification_sourd.*
import kotlinx.android.synthetic.main.activity_authentification_sourd.button


class authentification_sourd : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification_sourd)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        button1.setOnClickListener {
            startActivity(Intent(this, creercompte::class.java))
            finish()
        }
        button.setOnClickListener {
            doLing()
        }

    }

    private fun doLing() {
        if (login.text.toString().isEmpty()) {
            login.error = "entrez votre adresse email s'il vous plait"
            login.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(login.toString()).matches()) {
            login.error = "entrez une adresse email valide s'il vous plait"
            login.requestFocus()
            return
        }
        if (passe.text.toString().isEmpty()) {
            passe.error = "entrez votre mot de passe s'il vous plait"
            passe.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(passe.text.toString(),login.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser
                    updateUI(user)
                } else {


                    updateUI(null)
                }

                // ...
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {

    if (currentUser!=null){
        if(currentUser.isEmailVerified){
        startActivity(Intent(this,Dashboard::class.java))
    }else{
            Toast.makeText(baseContext, "Vérifiez votre adresse email s'il vous plait ",
                Toast.LENGTH_SHORT).show()
        }
    }
        else{
        Toast.makeText(baseContext, "Vérifiez votre mot de passe s'il vous plait",
            Toast.LENGTH_SHORT).show()
    }

    }

}