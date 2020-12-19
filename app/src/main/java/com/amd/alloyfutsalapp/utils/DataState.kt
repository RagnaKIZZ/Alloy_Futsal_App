package com.amd.alloyfutsalapp.utils

import okhttp3.ResponseBody

sealed class DataState<T>(
   val data : T? = null,
   val msg: String? = null,
   val errorBody: ResponseBody? = null
) {

    class Success<T>(data: T) : DataState<T>(data)
    class Error<T>(message: String, errorData: ResponseBody?) : DataState<T>(null, message, errorData)
    class Loading<T> : DataState<T>()
    class NotInternet<T>() : DataState<T>()
    class Error400Above<T>(errorData: ResponseBody?) : DataState<T>(null, null, errorData)

}