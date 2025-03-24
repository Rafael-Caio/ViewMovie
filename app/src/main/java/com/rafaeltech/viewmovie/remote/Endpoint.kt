package com.rafaeltech.viewmovie.api


import com.rafaeltech.viewmovie.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Endpoint {
    @GET("movie/popular")
    suspend fun getMovies(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("page") page: Int): Response<MovieResponse>
}