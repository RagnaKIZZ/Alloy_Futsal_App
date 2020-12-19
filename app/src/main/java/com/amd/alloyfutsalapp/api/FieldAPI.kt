package com.amd.alloyfutsalapp.api

import com.amd.alloyfutsalapp.model.ModelField
import retrofit2.Response
import retrofit2.http.GET

interface FieldAPI {

    @GET("field")
    suspend fun getField() : Response<ModelField>

}