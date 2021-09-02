package pessoto.android.mercadofinanceiro.model

import br.com.pessoto.mercadofinanceiro.R
import com.google.gson.annotations.SerializedName

class StockRecommendation {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("symbol")
    var symbol: String = ""

    @SerializedName("curent_price")
    var currentPrice: String = ""

    @SerializedName("target_price")
    var targetPrice: String = ""

    @SerializedName("potential_price")
    var potentialPrice: String = ""

    @SerializedName("recomendation")
    private var _recomendation: String = ""

    val recommendation: String
        get() {
            return when (_recomendation) {
                "buy" -> "Compra"
                "sell" -> "Venda"
                "neutral" -> "Neutro"
                else -> "Restrito"
            }
        }

    @SerializedName("date")
    var date: String = ""

    @SerializedName("link")
    var link: String = ""

    @SerializedName("symbol_image_url")
    var imageUrl: String = ""

    @SerializedName("company_name")
    var companyName: String = ""

    fun getRecommendationDrawble(): Int {
        return when (_recomendation) {
            "buy" -> R.drawable.backgroud_recommendation_buy
            "sell" -> R.drawable.backgroud_recommendation_sell
            "neutral" -> R.drawable.backgroud_recommendation_neutral
            else -> R.drawable.backgroud_recommendation_restricted
        }
    }
}
