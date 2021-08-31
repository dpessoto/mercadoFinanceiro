package br.com.pessoto.mercadofinanceiro.data.network

import com.google.gson.JsonObject
import retrofit2.http.GET

interface ApiService {

    @GET("estimates?origin=XP&is_source_favorite_recomendation=true")
    suspend fun getRecommendation() : JsonObject

}