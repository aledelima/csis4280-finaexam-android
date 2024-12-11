package br.com.solanches.retrofit.api

import br.com.solanches.retrofit.data.Movie
import retrofit2.http.GET

interface MovieApiService {

    @GET("movies")
    suspend fun getMovies(): List<Movie>
}