package com.dasaevcompany.upax.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dasaevcompany.upax.data.usecase.MovieUseCase
import com.dasaevcompany.upax.model.Movie
import com.dasaevcompany.upax.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val service: MovieUseCase
): ViewModel() {

    var movies =  MutableLiveData<List<Movie>>()

    var isLoading = MutableLiveData<Boolean>()
    var isFailed = MutableLiveData<Boolean>()


    fun getMovie(){
        viewModelScope.launch {
            service.getMovies(object : Callback<Result> {
                override fun onResponse(call: Call<Result>, response: Response<Result>) {
                    val data = response.body()
                    val list = data?.results
                    movies.postValue(list!!)
                    processFinished(false)
                }
                override fun onFailure(call: Call<Result>, t: Throwable) {
                    processFinished(true)
                }
            })
        }
    }

    fun processFinished(failed : Boolean){
        isLoading.value = false
        isFailed.value = failed
    }
}