package com.egci428.practice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egci428.practice.model.User
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader



class MainActivity : AppCompatActivity() {

    var uname: String? = null
    var pname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signInBtn = findViewById<Button>(R.id.signInBtn)
        val signUpBtn = findViewById<Button>(R.id.signUpBtn)
        val cancelBtn = findViewById<Button>(R.id.cancelBtn)
        val userText = findViewById<EditText>(R.id.userText)
        val passText = findViewById<EditText>(R.id.passText)

        signUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        signInBtn.setOnClickListener {
            val username = userText.text.toString()
            val password = passText.text.toString()

            val file = File(filesDir, "users.txt")
            val users = file.readLines()

            var signedIn = false
            for (user in users) {
                val parts = user.split(",")
                if (parts[0] == username && parts[1] == password) {
                    signedIn = true
                    break
                }
            }

            if (signedIn) {
                // Save the username to SharedPreferences
                val prefs = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString("username", username)
                editor.apply()

                val intent = Intent(this, JobListActivity::class.java)
                startActivity(intent)
            } else {
                // Show an error message
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }


        cancelBtn.setOnClickListener {
            userText.text = null
            passText.text = null
        }
    }

    private fun verifyCredentials(username: String, password: String): Boolean {
        val userFile = applicationContext.openFileInput("users.txt").bufferedReader().useLines { lines ->
            lines.fold("") { some, text -> "$some\n$text" }
        }
        val userList = userFile.split("\n")
        userList.forEach {
            val userDetail = it.split(",")
            if (userDetail[0] == username && userDetail[1] == password) {
                return true
            }
        }
        return false
    }
}

