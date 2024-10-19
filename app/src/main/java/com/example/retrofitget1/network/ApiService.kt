package com.example.retrofitget1.network

import com.example.retrofitget1.model.PockemonsItem
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory

//Interface containing API
interface ApiService{

    @GET("pokemon")
    suspend fun  getAllPokemon():List<PockemonsItem>
}
private var BASE_URL="https://tyradex.tech/api/v1/"

private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create()) // For JSON parsing
    .baseUrl(BASE_URL)
    .build()

object PockApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }}