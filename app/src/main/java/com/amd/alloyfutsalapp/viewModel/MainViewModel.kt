package com.amd.alloyfutsalapp.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.amd.alloyfutsalapp.FieldRepo
import com.amd.alloyfutsalapp.model.DataItem
import com.amd.alloyfutsalapp.model.ModelField
import com.amd.alloyfutsalapp.utils.DataState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class MainViewModel(
    val repo: FieldRepo
) : ViewModel() {

    private val TAG = "MainViewModel"

    val itemField: MutableLiveData<DataState<ModelField>> = MutableLiveData()
    val listData: MutableLiveData<List<DataItem>> = MutableLiveData()
    private val _paramSearch: MutableLiveData<String> = MutableLiveData()
    private val _listItem = ArrayList<DataItem>()

    init {
        loadDataItems()
    }

    val filteredList: LiveData<List<DataItem>> = Transformations
        .switchMap(_paramSearch) {
            _filterList(_paramSearch.value)
        }

    fun setParamSearch(text: String) {
        if (_paramSearch.value == text) {
            return
        }
        _paramSearch.value = text
    }

    fun setDataListItem(list: List<DataItem>) {
        if (_listItem == list) {
            return
        }
        _listItem.apply {
            clear()
            addAll(list)
        }
    }

    fun getBookmarkList(listData: List<DataItem>): List<DataItem> {
        viewModelScope.launch {
            withContext(IO) {
                for (i in listData.indices) {
                    val isBookmark = repo.getItem(listData[i].idField)
                    withContext(Main) {
                        listData[i].isBookmarked = isBookmark > 0
                    }
                }
            }
        }
        Log.d(TAG, "getBookmarkList: ${listData.toString()}")
        return listData
    }

    private fun _filterList(param: String?): LiveData<List<DataItem>> {
        return object : LiveData<List<DataItem>>() {
            override fun onActive() {
                super.onActive()
                val listFiltered = ArrayList<DataItem>()
                if (param!!.isNotEmpty()) {
                    for (i in _listItem.indices) {
                        if (_listItem[i].nameField.toLowerCase().contains(param.toLowerCase())) {
                            listFiltered.add(_listItem[i])
                            value = listFiltered
                        }
                    }
                } else {
                    listFiltered.clear()
                    value = listFiltered
                }
            }
        }
    }

    fun loadDataItems() = viewModelScope.launch {
        try {

            Log.d(TAG, "loadDataItems: run")

            itemField.postValue(DataState.Loading())

            val response = repo.getField()

            itemField.postValue(handleFieldResponse(response))

        } catch (t: Throwable) {

            Log.d(TAG, "loadDataItems: error")

            when (t) {
                is HttpException -> {
                    Log.d(TAG, "getBreakingNews: ${t.code()}")
                    itemField.postValue(DataState.Error400Above(t.response()!!.errorBody()))
                }

                is IOException -> {
                    Log.d(TAG, "getBreakingNews: ${t.message}")
                    itemField.postValue(DataState.NotInternet())
                }

                else -> {
                    Log.d(TAG, "loadDataItems: else")
                }
            }
        }
    }

    fun getSavedList() = repo.getSavedField()

    private fun handleFieldResponse(response: Response<ModelField>): DataState<ModelField> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return DataState.Success(result)
            }
        }

        return DataState.Error(response.message(), response.errorBody())
    }

}