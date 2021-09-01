package pessoto.android.mercadofinanceiro.data

import pessoto.android.mercadofinanceiro.data.network.NetworkClient

class DataSourceRemote {

    private val service = NetworkClient().service()

    suspend fun getRecommendation() = service.getRecommendation()

}
