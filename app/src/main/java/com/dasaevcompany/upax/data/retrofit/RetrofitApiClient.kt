package com.dasaevcompany.upax.data.retrofit

import com.dasaevcompany.upax.model.Result
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApiClient {

    @GET("popular?api_key=6895edbfb8343d6b17cb060d00a73ffc&language=es")
    fun getMovies(): Call<Result>
}
