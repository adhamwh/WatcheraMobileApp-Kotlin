package com.example.watchera

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.watchera.UserHandling.User
import com.example.watchera.UserHandling.UserDatabaseHelper
import com.example.watchera.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dbHelper: UserDatabaseHelper
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root) //  Only use this

        auth = FirebaseAuth.getInstance()
        dbHelper = UserDatabaseHelper(this)

        // Register button logic
        binding.btnRegister.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.etEmail.text.toString()
            val phoneNumber = binding.phoneNumberEditText.text.toString()
            val password = binding.etPassword.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty() && password.isNotEmpty()) {
                registerUser(email, password, name, phoneNumber)
            } else {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
            }
        }

        //  Login link click
        binding.loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun registerUser(email: String, password: String, name: String, phoneNumber: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userId = user!!.uid

                    val newUser = User(userId, name, email, phoneNumber)
                    dbHelper.addUser(newUser)

                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
