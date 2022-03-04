package com.dasaevcompany.upax.data.usecase

import com.dasaevcompany.upax.data.retrofit.RetrofitApiClient
import com.dasaevcompany.upax.model.Result
import retrofit2.Callback
import retrofit2.Retrofit
import javax.inject.Inject

class MovieUseCase @Inject constructor(retrofit: Retrofit) {

    private val retrofitService = retrofit.create(RetrofitApiClient::class.java)

    fun getMovies(callBack: Callback<Result>){
        retrofitService.getMovies().enqueue(callBack)
    }

}