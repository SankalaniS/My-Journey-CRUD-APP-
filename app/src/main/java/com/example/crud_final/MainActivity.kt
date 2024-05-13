package com.example.crud_final

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var InfoAdapter: InfoAdapter
    private lateinit var infoRecyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView and adapter
        infoRecyclerView = findViewById(R.id.infoRecycleView)
        infoRecyclerView.layoutManager = LinearLayoutManager(this)
        db = DatabaseHelper(this)
        InfoAdapter = InfoAdapter(db.getInfo(), this)
        infoRecyclerView.adapter = InfoAdapter

        // Initialize the FloatingActionButton using findViewById
        val addButton = findViewById<FloatingActionButton>(R.id.addButton)
        addButton.setOnClickListener {
            // Intent to start AddInfoActivity
            val intent = Intent(this, AddInfoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        InfoAdapter.refreshData(db.getInfo())
    }
}
