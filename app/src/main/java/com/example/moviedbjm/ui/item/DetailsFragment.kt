package com.example.moviedbjm.ui.item

import android.app.Application
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviedbjm.R
import com.example.moviedbjm.databinding.DetailsFragmentBinding
import com.example.moviedbjm.domain.Movie
import com.example.moviedbjm.domain.MovieRepositoryImpl
import com.example.moviedbjm.viewBinding
import com.example.moviedbjm.visibleOrGone

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

    private val viewModel: DetailsViewFragment by viewModels {
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

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val error = it ?: return@observe
            viewBinding.retry.visibility = View.VISIBLE
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            viewBinding.progress.visibleOrGone(it)
        }

        viewModel.movieLiveData.observe(viewLifecycleOwner) {
            val movie = arguments?.getParcelable<Movie>(BUNDLE_EXTRA) ?: return@observe

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

class MainViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        DetailsViewFragment(application, MovieRepositoryImpl()) as T
}

