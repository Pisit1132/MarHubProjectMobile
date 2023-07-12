package com.egci428.practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.egci428.practice.model.User
import com.egci428.practice.model.UserAdapter
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception

class UserListActivity : AppCompatActivity() {

    lateinit var userList: MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        val listView = findViewById<ListView>(R.id.listView)
        val logoutButton = findViewById<Button>(R.id.button2)

        userList = loadUsers()

        val adapter = UserAdapter(this, R.layout.userslist, userList)
        listView.adapter = adapter



        logoutButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do you really want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    fun loadUsers(): MutableList<User> {
        val users = mutableListOf<User>()
        val file = File(filesDir, "users.txt")
        if (file.exists()) {
            val inputAsString = FileInputStream(file).bufferedReader().use { it.readText() }
            val lines = inputAsString.split("\n")
            for (line in lines) {
                val parts = line.split(",")
                if(parts.size == 4) {
                    users.add(User(parts[0], parts[1],))
                }
            }
        }
        return users
    }



}


