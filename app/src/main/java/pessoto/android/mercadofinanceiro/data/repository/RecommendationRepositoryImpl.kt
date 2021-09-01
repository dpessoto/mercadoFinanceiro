package pessoto.android.mercadofinanceiro.data.repository

import br.com.pessoto.mercadofinanceiro.data.repository.RecommendationRepository
import br.com.pessoto.mercadofinanceiro.data.repository.ResultRepository
import pessoto.android.mercadofinanceiro.model.StockRecommendation
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import pessoto.android.mercadofinanceiro.data.DataSourceRemote

class RecommendationRepositoryImpl : RecommendationRepository {
    override suspend fun getRecommendation(): ResultRepository<List<StockRecommendation>> {
        val response = try {
            DataSourceRemote().getRecommendation()
        } catch (e: Exception) {
            return ResultRepository.Error(e)
        }

        val jsonObject: JSONObject
        try {
            jsonObject = JSONObject(Gson().toJson(response))
            val result = jsonObject.getJSONObject("message").getJSONArray("result")
            val length = result.length() - 1

            return if (jsonObject.optBoolean("status") && length > 0) {
                val list = ArrayList<StockRecommendation>()
                for (i in 0..length) {
                    list.add(
                        GsonBuilder().create().fromJson(
                            result.optJSONObject(i).toString(),
                            StockRecommendation::class.java
                        )
                    )
                }

                ResultRepository.Success(list)
            } else {
                ResultRepository.Error(Exception())
            }
        } catch (e: JSONException) {
            return ResultRepository.Error(e)
        }

    }
}
