package br.com.pessoto.mercadofinanceiro.data

import br.com.pessoto.mercadofinanceiro.data.network.NetworkClient

class DataSourceRemote {

    private val service = NetworkClient().service()

    suspend fun getRecommendation() = service.getRecommendation()

}
