package com.dasaevcompany.upax.data.database.provider

import androidx.room.*
import com.dasaevcompany.upax.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    suspend fun getMovies() : List<Movie>

    @Query("DELETE FROM Movie")
    suspend fun deleteAllMovies()

    @Delete
    suspend fun deleteMovie(Movie: Movie)

    @Update
    suspend fun updateMovie(Movie: Movie)

    @Insert
    suspend fun addMovie(Movie: Movie)
}