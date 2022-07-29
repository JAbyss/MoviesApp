package com.example.moviesapp

import com.example.moviesapp.network.models.ResponseFilms
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmApi {

    @GET("./svc/movies/v2/reviews/all.json")
    suspend fun getAllFilms(
        @Query("api-key") api_key: String = "ookcqm0SgYCEwAGIGOTxpAG8EO5Uzw4X"
    ): Response<ResponseFilms>

    @GET("./svc/movies/v2/reviews/all.json")
    suspend fun getOffsetFilms(
        @Query("api-key") api_key: String = "ookcqm0SgYCEwAGIGOTxpAG8EO5Uzw4X",
        @Query("offset") offset: Int
    ): Response<ResponseFilms>

}