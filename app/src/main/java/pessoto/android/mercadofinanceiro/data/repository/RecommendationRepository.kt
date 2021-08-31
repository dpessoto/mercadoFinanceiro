package br.com.pessoto.mercadofinanceiro.data.repository

import br.com.pessoto.mercadofinanceiro.model.StockRecommendation

interface RecommendationRepository {
    suspend fun getRecommendation() : ResultRepository<List<StockRecommendation>>
}