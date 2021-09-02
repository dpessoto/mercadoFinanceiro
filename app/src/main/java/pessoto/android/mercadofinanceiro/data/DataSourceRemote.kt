package pessoto.android.mercadofinanceiro.data

import pessoto.android.mercadofinanceiro.data.network.NetworkClient

class DataSourceRemote {

    private val service = NetworkClient().service()

    suspend fun getAllRecommendation() = service.getAllRecommendation()
    suspend fun getBuyRecommendation() = service.getBuyRecommendation()
    suspend fun getSellRecommendation() = service.getSellRecommendation()
    suspend fun getNeutralRecommendation() = service.getNeutralRecommendation()
    suspend fun getRestrictedRecommendation() = service.getRestrictedRecommendation()
}
