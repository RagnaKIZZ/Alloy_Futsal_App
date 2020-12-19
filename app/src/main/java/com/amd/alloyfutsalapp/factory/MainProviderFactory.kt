package com.amd.alloyfutsalapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amd.alloyfutsalapp.FieldRepo
import com.amd.alloyfutsalapp.viewModel.MainViewModel

class MainProviderFactory(
    val repo: FieldRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }
}