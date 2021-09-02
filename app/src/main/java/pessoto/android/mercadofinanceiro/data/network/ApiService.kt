package pessoto.android.mercadofinanceiro.data.network

import com.google.gson.JsonObject
import retrofit2.http.GET

interface ApiService {

    @GET("estimates?origin=XP&is_source_favorite_recomendation=false&recommendations[]=restricted&recommendations[]=buy&recommendations[]=sell&recommendations[]=neutral")
    suspend fun getAllRecommendation() : JsonObject

}