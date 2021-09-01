package pessoto.android.mercadofinanceiro.data.repository

import br.com.pessoto.mercadofinanceiro.data.repository.RecommendationRepository
import br.com.pessoto.mercadofinanceiro.data.repository.ResultRepository
import com.google.gson.GsonBuilder
import pessoto.android.mercadofinanceiro.data.DataSourceRemote
import pessoto.android.mercadofinanceiro.model.HubResponse
import pessoto.android.mercadofinanceiro.model.StockRecommendation

class RecommendationRepositoryImpl : RecommendationRepository {
    override suspend fun getRecommendation(): ResultRepository<List<StockRecommendation>> {
        try {
            val result = HubResponse(DataSourceRemote().getRecommendation()).getResult()
            return if (result != null) {
                val resultArray = result.first
                val range = result.second
                val list = ArrayList<StockRecommendation>()

                for (i in range) {
                    list.add(GsonBuilder().create().fromJson(resultArray.optJSONObject(i).toString(), StockRecommendation::class.java))
                }

                ResultRepository.Success(list)
            } else {
                ResultRepository.Error(Exception())
            }
        } catch (e: Exception) {
            return ResultRepository.Error(e)
        }
    }
}
