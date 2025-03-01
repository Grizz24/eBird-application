package com.example.icarus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginButton = findViewById<Button>(R.id.login)
        val signUpButton = findViewById<Button>(R.id.register)

        loginButton.setOnClickListener{
            Toast.makeText(this, "Login button clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        signUpButton.setOnClickListener {
            Toast.makeText(this, "Register button clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}