package com.hsdroid.pagedlist.data.network

import com.hsdroid.pagedlist.data.models.ServerResponse
import retrofit2.http.GET

interface APIService {

    @GET("posts")
    suspend fun getResponse() : List<ServerResponse>
}