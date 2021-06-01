package com.example.moviedbjm.ui.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.moviedbjm.R
import com.example.moviedbjm.databinding.MoviesFragmentBinding
import com.example.moviedbjm.ui.main.MoviesCategoriesAdapter
import com.example.moviedbjm.viewBinding

class MoviesFragment : Fragment(R.layout.movies_fragment) {

    private val viewBinding: MoviesFragmentBinding by viewBinding(
        MoviesFragmentBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.moviesList.adapter = MoviesCategoriesAdapter()
    }
}