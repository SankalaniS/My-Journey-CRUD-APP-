package com.example.crud_final

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class FirstPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener { // Start the main activity
            val intent = Intent(
                this@FirstPage,
                MainActivity::class.java
            )
            startActivity(intent)
        }
    }
}

