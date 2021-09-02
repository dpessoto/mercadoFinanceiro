package pessoto.android.mercadofinanceiro.data.repository

import pessoto.android.mercadofinanceiro.model.StockRecommendation

interface RecommendationRepository {
    suspend fun getAllRecommendation() : ResultRepository<List<StockRecommendation>>
}