package com.amd.alloyfutsalapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amd.alloyfutsalapp.FieldRepo
import com.amd.alloyfutsalapp.model.DataItem
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repo: FieldRepo
) : ViewModel(){

    fun deleteField(data: DataItem) = viewModelScope.launch {
        repo.delField(data)
    }

    fun upsertField(data: DataItem) = viewModelScope.launch {
        repo.saveField(data)
    }

}
