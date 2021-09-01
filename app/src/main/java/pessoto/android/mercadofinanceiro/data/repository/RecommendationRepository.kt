package br.com.pessoto.mercadofinanceiro.data.repository

import pessoto.android.mercadofinanceiro.model.StockRecommendation

interface RecommendationRepository {
    suspend fun getRecommendation() : ResultRepository<List<StockRecommendation>>
}