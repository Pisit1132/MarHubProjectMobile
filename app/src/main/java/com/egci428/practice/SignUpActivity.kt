package com.egci428.practice

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.egci428.practice.model.User
import java.util.*

class SignUpActivity : AppCompatActivity() {

    lateinit var latText: EditText
    lateinit var lonText: EditText
    lateinit var userSignText: EditText
    lateinit var passSignText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        val addBtn = findViewById<Button>(R.id.addBtn)



        userSignText = findViewById(R.id.userSignText)
        passSignText = findViewById(R.id.passSignText)



        addBtn.setOnClickListener {
            saveData()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }



    private fun saveData() {
        val uname = userSignText.text.toString()
        val pass = passSignText.text.toString()

        val user = User(uname, pass,)
        val userFile = "${user.username},${user.password}"
        applicationContext.openFileOutput("users.txt", Context.MODE_APPEND).use {
            it.write("$userFile\n".toByteArray())
        }
    }
}

