package com.example.watchera

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.watchera.UserHandling.UserDatabaseHelper
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var dbHelper: UserDatabaseHelper
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        dbHelper = UserDatabaseHelper(this)


        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }


        val userId = auth.currentUser?.uid
        if (userId != null) {
            // Fetch user data from SQLite
            val user = dbHelper.getUserById(userId)
            if (user != null) {
                findViewById<TextView>(R.id.nameTextView).text = user.name
                findViewById<TextView>(R.id.emailTextView).text = user.email
                findViewById<TextView>(R.id.phoneTextView).text = user.phone
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
