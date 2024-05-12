package com.example.crud_final

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class AddInfoActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var saveButton: ImageView
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_info)

        titleEditText = findViewById(R.id.titleEditText)
        contentEditText = findViewById(R.id.contentEditText)
        saveButton = findViewById(R.id.saveButton)

        db = DatabaseHelper(this)

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()
            // Validate title
            if (title.isEmpty()) {
                titleEditText.error = "Title cannot be empty"
                return@setOnClickListener
            }

            // Validate content
            if (content.isEmpty()) {
                contentEditText.error = "Content cannot be empty"
                return@setOnClickListener
            }

            // If both title and content are not empty, proceed with saving the info
            val info = Info(0, title, content)
            db.insertInfo(info)
            finish()
            Toast.makeText(this, "Info Added", Toast.LENGTH_SHORT).show()
        }
    }
}
