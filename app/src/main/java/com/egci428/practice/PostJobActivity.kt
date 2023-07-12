package com.egci428.practice



import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import java.io.File
import com.egci428.practice.Job

class PostJobActivity : AppCompatActivity() {
    private lateinit var descriptionInput: EditText
    private lateinit var budgetInput: EditText
    private lateinit var contactInput: EditText
    private lateinit var postButton: Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_job)

        descriptionInput = findViewById(R.id.description_input)
        budgetInput = findViewById(R.id.budget_input)
        contactInput = findViewById(R.id.contact_input)
        postButton = findViewById(R.id.post_button)
        database = FirebaseDatabase.getInstance().getReference("jobs")

        postButton.setOnClickListener {
            val prefs = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
            val username = prefs.getString("username", "")
            val description = descriptionInput.text.toString()
            val budget = budgetInput.text.toString()
            val contact = contactInput.text.toString()

            val job = Job(username!!, description, budget, contact)
            database.child(username).setValue(job)

            // Move to JobListActivity
            val intent = Intent(this, JobListActivity::class.java)
            startActivity(intent)
        }
    }
}

