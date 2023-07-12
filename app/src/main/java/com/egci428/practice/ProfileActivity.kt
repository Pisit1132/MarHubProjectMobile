package com.egci428.practice

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

class ProfileActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var cameraButton: Button
    private lateinit var listView: ListView
    private lateinit var switchButton: Button
    private lateinit var usernameTextView: TextView

    private val REQUEST_IMAGE_CAPTURE = 1
    private val MY_PERMISSIONS_REQUEST_CAMERA = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        imageView = findViewById(R.id.profile_image)
        cameraButton = findViewById(R.id.camera_button)
        switchButton = findViewById(R.id.switch_button)
        usernameTextView = findViewById(R.id.username_text_view)

        val prefs = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        val username = prefs.getString("username", "")
        usernameTextView.text = username

        cameraButton.setOnClickListener {
            checkCameraPermission()
        }

        switchButton.setOnClickListener {
            // Switch to influencer
        }

        val file = File(filesDir, "jobs.txt")
        val jobs = file.readLines().mapNotNull {
            val parts = it.split(",")
            if (parts.size >= 3) {
                "Posted by ${parts[0]}\n${parts[1]}\nBudget: ${parts[2]}"
            } else {
                null
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),
                MY_PERMISSIONS_REQUEST_CAMERA)
        } else {
            dispatchTakePictureIntent()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CAMERA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    dispatchTakePictureIntent()
                } else {
                    Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
            else -> {
                // Ignore all other requests
            }
        }
    }
}


