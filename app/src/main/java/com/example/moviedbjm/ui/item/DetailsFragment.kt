package com.example.moviedbjm.ui.item

import android.app.Application
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.moviedbjm.R
import com.example.moviedbjm.databinding.DetailsFragmentBinding
import com.example.moviedbjm.domain.Movie
import com.example.moviedbjm.domain.MovieRepositoryImpl
import com.example.moviedbjm.viewBinding
import com.example.moviedbjm.visibleOrGone
import kotlinx.coroutines.flow.collect

class DetailsFragment : Fragment(R.layout.details_fragment) {
    companion object {
        const val BUNDLE_EXTRA = "MOVIE"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewBinding: DetailsFragmentBinding by viewBinding(
        DetailsFragmentBinding::bind
    )

    private val viewModel: DetailsViewModel by viewModels {
        MainViewModelFactory(requireActivity().application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.fetchMovie()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.retry.setOnClickListener {
            viewModel.fetchMovie()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.error.collect {
                it?.let {
                    viewBinding.retry.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loading.collect {
                viewBinding.progress.visibleOrGone(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.movie.collect {
                val movie = arguments?.getParcelable<Movie>(BUNDLE_EXTRA) ?: return@collect

                with(viewBinding) {
                    retry.visibility = View.GONE

                    detailMovieTitle.text = movie.title
                    detailOverviewTitle.text = movie.overview
                    detailReleaseDateTitle.text = movie.releaseDate
                    detailVoteAverageDateTitle.text = movie.voteAverage.toString()
                    Glide.with(detailMovieImage)
                        .load(movie.posterPath)
                        .into(detailMovieImage)
                }
            }
        }
    }
}

class MainViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        DetailsViewModel(application, MovieRepositoryImpl()) as T
}

