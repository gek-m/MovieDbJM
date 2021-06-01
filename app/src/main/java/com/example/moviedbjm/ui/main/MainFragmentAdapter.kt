package com.example.moviedbjm.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedbjm.databinding.MovieCardBinding
import com.example.moviedbjm.domain.Movie

typealias ItemClicked = (movie: Movie) -> Unit

class MainFragmentAdapter(private val itemCLicked: ItemClicked? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = mutableListOf<Movie>()

    fun setData(dataToSet: List<Movie>) {
        data.apply {
            clear()
            addAll(dataToSet)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val moviesView =
            MovieCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(moviesView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        (holder as? MainViewHolder)?.let { movieHolder ->
            movieHolder.bind(item)
        }
    }

    override fun getItemCount() = data.size

    inner class MainViewHolder(val binding: MovieCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieReleaseDate.text = movie.releaseDate

            Glide.with(binding.movieImage)
                .load(movie.posterPath)
                .into(binding.movieImage)
        }

        init {
            itemView.setOnClickListener {
                itemCLicked?.invoke(data[adapterPosition])
            }
        }
    }
}