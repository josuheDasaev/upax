package com.dasaevcompany.upax.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dasaevcompany.upax.data.database.provider.MovieAccess
import com.dasaevcompany.upax.model.Movie
import com.dasaevcompany.upax.utilities.CallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDBViewModel@Inject constructor(
    private var database : MovieAccess) : ViewModel() {

    var listMovie = MutableLiveData<List<Movie>>()
    var added = MutableLiveData<Boolean>()

    var isLoading = MutableLiveData<Boolean>()
    var error = MutableLiveData<Boolean>()

    fun getMovies(){
        viewModelScope.launch {
            database.getMovie(object : CallBack<List<Movie>> {
                override fun onSuccess(result: List<Movie>?) {
                    listMovie.postValue(result!!)
                    processFinished(false)
                }
                override fun onFailed(exception: Exception?) {
                    processFinished(true)
                }
            })
        }
    }

    fun addListMovies(list: List<Movie>){
        var cont = 0
        for (movie in list){
            viewModelScope.launch {
                database.addMovie(movie, object : CallBack<Boolean>{
                    override fun onSuccess(result: Boolean?) {
                        cont += 1
                        if (cont == list.size){
                            added.postValue(result!!)
                            processFinished(false)
                        }
                    }
                    override fun onFailed(exception: Exception?) {
                        processFinished(true)
                    }
                })
            }
        }
    }

    private fun processFinished(failed: Boolean){
        isLoading.value = false
        error.value = failed
    }
}