package com.example.moviedbjm.ui.main

import android.app.Application
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbjm.R
import com.example.moviedbjm.databinding.MainFragmentBinding
import com.example.moviedbjm.domain.MovieRepositoryImpl
import com.example.moviedbjm.router.RouterHolder
import com.example.moviedbjm.storage.MovieStorage
import com.example.moviedbjm.ui.item.DetailsFragment
import com.example.moviedbjm.viewBinding
import com.example.moviedbjm.visibleOrGone
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment(R.layout.main_fragment) {

    private val adapter = MainFragmentAdapter {
        (activity as? RouterHolder)?.router?.openMovieDetails(
            Bundle().apply {
                putParcelable(DetailsFragment.BUNDLE_EXTRA, it)
            }
        )
    }

    private val viewBinding: MainFragmentBinding by viewBinding(
        MainFragmentBinding::bind
    )

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            application = requireActivity().application,
            movieStorage = MovieStorage(requireContext())
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.fetchMovies()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.topMovieList.adapter = adapter

        viewBinding.topMovieList.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        viewBinding.swipeRefresh.setOnRefreshListener {
            viewModel.fetchMovies()
            viewBinding.swipeRefresh.isRefreshing = false
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loading.collect {
                viewBinding.progressBar.visibleOrGone(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.movies.collect {
                adapter.apply {
                    setData(it)
                    notifyDataSetChanged()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.error.collect {
                val error = it
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

class MainViewModelFactory(
    private val application: Application,
    private val movieStorage: MovieStorage
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainViewModel(application, MovieRepositoryImpl(), movieStorage) as T
}