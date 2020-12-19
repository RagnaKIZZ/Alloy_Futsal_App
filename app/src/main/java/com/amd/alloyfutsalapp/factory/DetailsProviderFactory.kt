package com.amd.alloyfutsalapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amd.alloyfutsalapp.FieldRepo
import com.amd.alloyfutsalapp.viewModel.DetailsViewModel

class DetailsProviderFactory(
    val repo: FieldRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(repo) as T
    }
}