package com.bignerdranch.android.cinemaapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NavigationSetFragment : Fragment() {
    private lateinit var movieDao: MovieDao
    private lateinit var adapter: MovieAdapter
    private lateinit var selectedImageUri: Uri
    private lateinit var selectedImageView: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_navigation_set, container, false)



        val recyclerView = view.findViewById<RecyclerView>(R.id.setRecyclerView).apply {
            layoutManager = LinearLayoutManager(requireContext())
        }

        val database = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "movies-dbase"
        ).build()
        movieDao = database.movieDao()

        recyclerView.adapter = adapter

        loadMovies()

        val createSetButton = view.findViewById<Button>(R.id.createSetButton)
        createSetButton.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), createSetButton)
            popupMenu.inflate(R.menu.menu_add)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_add -> {
                        val intent = Intent(requireContext(), AddMovieScreen::class.java)
                        startActivityForResult(intent, REQUEST_CREATE_MOVIES)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

        return view
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
        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            selectedImageView.visibility = View.VISIBLE
            selectedImageView.setImageURI(selectedImageUri)
            if (selectedImageUri != null) {
                this.selectedImageUri = selectedImageUri
            }
        }
    }




    companion object {
        private const val REQUEST_SELECT_IMAGE = 100
        private const val REQUEST_MOVIE_DETAILS = 101
        private const val REQUEST_CREATE_MOVIES = 102

    }
}