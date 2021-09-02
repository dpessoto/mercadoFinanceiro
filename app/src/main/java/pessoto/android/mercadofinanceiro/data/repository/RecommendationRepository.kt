package pessoto.android.mercadofinanceiro.data.repository

import pessoto.android.mercadofinanceiro.model.StockRecommendation

interface RecommendationRepository {
    suspend fun getAllRecommendation() : ResultRepository<List<StockRecommendation>>
    suspend fun getBuyRecommendation() : ResultRepository<List<StockRecommendation>>
    suspend fun getSellRecommendation() : ResultRepository<List<StockRecommendation>>
    suspend fun getNeutralRecommendation() : ResultRepository<List<StockRecommendation>>
    suspend fun getRestrictedRecommendation() : ResultRepository<List<StockRecommendation>>
}