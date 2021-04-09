package com.satryaway.bcgdvtest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    fun searchSong(
        @Query("term") term: String
    ): Call<ResultSongModel>
}