package com.example.icarus

import com.example.icarus.BirdLocation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface EbirdApiService {
    @GET("data/obs/geo/recent")
    fun getBirdData(
        @Header("X-eBirdApiToken") apiKey: String,
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("speciesCode") speciesCode: String,
        @Query("dist") dist: Int
    ): Call<List<BirdLocation>>
}