package pessoto.android.mercadofinanceiro.data.repository

import com.google.gson.GsonBuilder
import org.json.JSONArray
import pessoto.android.mercadofinanceiro.data.DataSourceRemote
import pessoto.android.mercadofinanceiro.model.HubResponse
import pessoto.android.mercadofinanceiro.model.StockRecommendation

class RecommendationRepositoryImpl : RecommendationRepository {

    private fun buildResult(result: Pair<JSONArray, IntRange>?): ResultRepository<List<StockRecommendation>> {
        return if (result != null) {
            val resultArray = result.first
            val range = result.second
            val list = ArrayList<StockRecommendation>()

            for (i in range) {
                list.add(
                    GsonBuilder().create().fromJson(
                        resultArray.optJSONObject(i).toString(),
                        StockRecommendation::class.java
                    )
                )
            }

            ResultRepository.Success(list)
        } else {
            ResultRepository.Error(Exception())
        }
    }

    override suspend fun getAllRecommendation(): ResultRepository<List<StockRecommendation>> {
        return try {
            buildResult(HubResponse(DataSourceRemote().getAllRecommendation()).getResult())
        } catch (e: Exception) {
            ResultRepository.Error(e)
        }
    }

    override suspend fun getBuyRecommendation(): ResultRepository<List<StockRecommendation>> {
        return try {
            buildResult(HubResponse(DataSourceRemote().getBuyRecommendation()).getResult())
        } catch (e: Exception) {
            ResultRepository.Error(e)
        }
    }

    override suspend fun getSellRecommendation(): ResultRepository<List<StockRecommendation>> {
        return try {
            buildResult(HubResponse(DataSourceRemote().getSellRecommendation()).getResult())
        } catch (e: Exception) {
            ResultRepository.Error(e)
        }
    }

    override suspend fun getNeutralRecommendation(): ResultRepository<List<StockRecommendation>> {
        return try {
            buildResult(HubResponse(DataSourceRemote().getNeutralRecommendation()).getResult())
        } catch (e: Exception) {
            ResultRepository.Error(e)
        }
    }

    override suspend fun getRestrictedRecommendation(): ResultRepository<List<StockRecommendation>> {
        return try {
            buildResult(HubResponse(DataSourceRemote().getRestrictedRecommendation()).getResult())
        } catch (e: Exception) {
            ResultRepository.Error(e)
        }
    }
}
