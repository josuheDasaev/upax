package com.dasaevcompany.upax.data.database.provider

import com.dasaevcompany.upax.data.database.room.RoomDatabase
import com.dasaevcompany.upax.model.Movie
import com.dasaevcompany.upax.utilities.CallBack
import java.lang.Exception
import javax.inject.Inject

class MovieAccess @Inject constructor(database: RoomDatabase){

    private val table = database.movieDao()

    suspend fun getMovie(callBack: CallBack<List<Movie>>){
        try { callBack.onSuccess(table.getMovies())
        }catch (e: Exception){ callBack.onFailed(e) }
    }

    suspend fun addMovie(movie: Movie, callBack: CallBack<Boolean>){
        try {
            table.addMovie(movie)
            callBack.onSuccess(true)
        }catch (e: Exception){ callBack.onFailed(e) }
    }

    suspend fun updateMovie(movie: Movie, callBack: CallBack<Boolean>){
        try {
            table.updateMovie(movie)
            callBack.onSuccess(true)
        }catch (e: Exception){ callBack.onFailed(e) }
    }

    suspend fun deleteMovie(movie: Movie, callBack: CallBack<Boolean>){
        try {
            table.deleteMovie(movie)
            callBack.onSuccess(true)
        }catch (e: Exception){ callBack.onFailed(e) }
    }
}