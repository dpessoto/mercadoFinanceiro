package br.com.pessoto.mercadofinanceiro.data.repository

import br.com.pessoto.mercadofinanceiro.data.DataSourceRemote
import br.com.pessoto.mercadofinanceiro.model.StockRecommendation
import java.lang.Exception
import org.json.JSONException

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONArray

import org.json.JSONObject


class RecommendationRepositoryImpl : RecommendationRepository {
    override suspend fun getRecommendation(): ResultRepository<List<StockRecommendation>> {
        val response = DataSourceRemote().getRecommendation()
        val jsonObject: JSONObject
        try {
            jsonObject = JSONObject(Gson().toJson(response))
            val result = jsonObject.getJSONObject("message").getJSONArray("result")
            val length = result.length() - 1

            return if (jsonObject.optBoolean("status") && length > 0) {
                val list = ArrayList<StockRecommendation>()
                for (i in 0..length) {
                    list.add(GsonBuilder().create().fromJson(result.optJSONObject(i).toString(), StockRecommendation::class.java))
                }

                ResultRepository.Success(list)
            } else {
                ResultRepository.Error(Exception("Erro!"))
            }
        } catch (e: JSONException) {
            return ResultRepository.Error(Exception("Erro!"))

        }

    }
}
