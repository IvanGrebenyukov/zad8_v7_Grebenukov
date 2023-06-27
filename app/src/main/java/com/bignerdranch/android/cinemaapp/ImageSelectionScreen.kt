package com.bignerdranch.android.cinemaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class ImageSelectionScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_selection_screen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val backButton = findViewById<ImageView>(R.id.backImage)
        backButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
        val image1 = findViewById<ImageView>(R.id.image1)
        image1.setOnClickListener { selectImage(R.drawable.image1) }
        val image2 = findViewById<ImageView>(R.id.image2)
        image2.setOnClickListener { selectImage(R.drawable.image2) }
        val image3 = findViewById<ImageView>(R.id.image3)
        image3.setOnClickListener { selectImage(R.drawable.image3) }
        val image4 = findViewById<ImageView>(R.id.image4)
        image4.setOnClickListener { selectImage(R.drawable.image4) }
        val image5 = findViewById<ImageView>(R.id.image5)
        image5.setOnClickListener { selectImage(R.drawable.image5) }
        val image6 = findViewById<ImageView>(R.id.image6)
        image6.setOnClickListener { selectImage(R.drawable.image6) }
    }
    private fun selectImage(iconId: Int) {
        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_SELECTED_IMAGE, iconId)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    companion object {
        const val EXTRA_SELECTED_IMAGE = "selected_image"
    }
}