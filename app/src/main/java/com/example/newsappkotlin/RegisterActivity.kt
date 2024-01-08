package com.example.newsappkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.example.newsappkotlin.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var alreadyRegistred: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alreadyRegistred = binding.alreadyRegistred // Replace with the actual ID from your layout

        alreadyRegistred.setOnClickListener {
            // Start the RegisterActivity when the register button is clicked
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val registerBtn = binding.registerBtn

        registerBtn.setOnClickListener {
            registerUser()
        }

    }

    private fun registerUser() {
        val username = binding.usernameInput.text.toString().trim()
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        val confirmPassword = binding.confirmPasswordInput.text.toString().trim()

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // Firebase authentication to create user
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // You can perform additional actions after successful registration here

                    val userId: String = auth.currentUser?.uid ?: "defaultUserId"

                    val user = hashMapOf(
                            "name" to username,
                            "email" to email,
                            "avatar" to "dfnbdfg",
                            "password" to password,
                    )

                    db.collection("users")
                        .document(userId) // You can use a dynamic ID or let Firestore generate one
                        .set(user)
                        .addOnSuccessListener {

                        }
                        .addOnFailureListener { e ->
                            // Handle errors
//                            println("Error inserting user: $e")
                            Toast.makeText(this, "Error inserting user : $e", Toast.LENGTH_SHORT).show()

                        }

                    // Registration successful
                    Toast.makeText(this, "Registration successful,UserId = $userId", Toast.LENGTH_SHORT).show()

                    // Redirect the user to the main activity
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("userName", username) //replaced with authentication
                    startActivity(intent)
                } else {
                    // If registration fails, display a message to the user.
                    Toast.makeText(
                        this,
                        "Registration failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}