package com.example.crud_final

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UpdateActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private var infoId: Int = -1
    private lateinit var updateTitleEditText: EditText
    private lateinit var updateContentEditText: EditText
    private lateinit var UpdateButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        // Initializing database helper
        db = DatabaseHelper(this)

        // Getting note ID from Intent
        infoId = intent.getIntExtra("info_id", -1)
        if (infoId == -1) {
            Toast.makeText(this, "Error: No info ID provided.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Initializing EditTexts and Button using findViewById
        updateTitleEditText = findViewById(R.id.updatetitle)
        updateContentEditText = findViewById(R.id.updatecontent)
        UpdateButton = findViewById(R.id.UpdateButton)

        // Load the note details into the EditTexts
        val info = db.getInfoById(infoId)
        updateTitleEditText.setText(info.title)
        updateContentEditText.setText(info.content)

        // Setting onClickListener for the update button
        UpdateButton.setOnClickListener {
            val newTitle = updateTitleEditText.text.toString()
            val newContent = updateContentEditText.text.toString()
            // Check if title is empty
            if (newTitle.isEmpty()) {
                updateTitleEditText.error = "Title cannot be empty"
                return@setOnClickListener
            }

            // Check if content is empty
            if (newContent.isEmpty()) {
                updateContentEditText.error = "Content cannot be empty"
                return@setOnClickListener
            }

            // Proceed with updating the information if both fields are not empty
            val updatedInfo = Info(infoId, newTitle, newContent)
            db.update(updatedInfo)
            Toast.makeText(this, "Info Edited!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
