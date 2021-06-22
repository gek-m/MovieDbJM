package com.example.moviedbjm.ui.settings

import android.app.Application
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.moviedbjm.R
import com.example.moviedbjm.databinding.SettingsFragmentBinding
import com.example.moviedbjm.storage.MovieStorage
import com.example.moviedbjm.viewBinding
import kotlinx.coroutines.flow.collect

class SettingsFragment : Fragment(R.layout.settings_fragment) {

    private val viewBinding: SettingsFragmentBinding by viewBinding(
        SettingsFragmentBinding::bind
    )

    private val viewModel: SettingsViewModel by viewModels {
        SettingViewModelFactory(requireActivity().application, MovieStorage(requireContext()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.showSettings()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.checkboxAdult.setOnClickListener {
            Toast.makeText(requireContext(), "Adult", Toast.LENGTH_SHORT).show()
            viewModel.isAdultFlagChange()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.text.collect {
                viewBinding.textSettings.text = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isAdultFlag.collect {
                viewBinding.checkboxAdult.isChecked = it
            }
        }
    }
}

class SettingViewModelFactory(
    private val application: Application,
    private val movieStorage: MovieStorage
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        SettingsViewModel(application, movieStorage) as T
}