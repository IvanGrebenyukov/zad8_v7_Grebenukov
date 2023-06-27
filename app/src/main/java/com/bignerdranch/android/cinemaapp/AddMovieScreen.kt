package com.bignerdranch.android.cinemaapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddMovieScreen : AppCompatActivity() {

    private val newMovie = mutableListOf<Movie>()
    private lateinit var movieDao: MovieDao
    private lateinit var adapter: MovieAdapter
    private lateinit var titleEditText: EditText
    private lateinit var authorEditText: EditText
    private lateinit var genreSpinner:Spinner
    private lateinit var addImageButton:Button
    private lateinit var selectedImageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie_screen)

        val titleEditText = findViewById<EditText>(R.id.titleEditText)
        val authorEditText = findViewById<EditText>(R.id.directorEditText)
        val genreSpinner = findViewById<Spinner>(R.id.genreSpinner)
        selectedImageView = findViewById(R.id.imageView)

        val addImageButton = findViewById<AppCompatButton>(R.id.addImageButton)
        addImageButton.setOnClickListener{
            val intent = Intent(this, ImageSelectionScreen::class.java)
            startActivityForResult(intent, REQUEST_SELECT_IMAGE)
        }
        val addMovieButton = findViewById<AppCompatButton>(R.id.addButton)
        addMovieButton.setOnClickListener{
            val title = titleEditText.text.toString()
            val author = authorEditText.text.toString()
            val genre = genreSpinner.selectedItem.toString()
            val image = selectedImageView.toString()
            if (title.isNotEmpty() && genre.isNotEmpty() && image.isNotEmpty() && author.isNotEmpty()) {
                val movie = Movie(icon = image, title = title, genre = genre, author = author)
                lifecycleScope.launch(Dispatchers.IO) {
                    movieDao.addMovie(movie)
                    withContext(Dispatchers.Main) {
                        loadMovies()
                    }
                }
            } else {
            }
        }
    }
    private fun loadMovies() {
        lifecycleScope.launch(Dispatchers.IO) {
            val movies = movieDao.getAllMovies()
            withContext(Dispatchers.Main) {
                adapter.setData(movies)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK) {
            val selectedIcon = data?.getIntExtra(ImageSelectionScreen.EXTRA_SELECTED_IMAGE, R.drawable.image1)
            if (selectedIcon != null) {
                val selectedImageView = findViewById<ImageView>(R.id.imageView)
                selectedImageView.setImageResource(selectedIcon)
                selectedImageView.tag = selectedIcon
            }
        }
    }
    companion object {
        private const val REQUEST_SELECT_IMAGE = 101
    }
}