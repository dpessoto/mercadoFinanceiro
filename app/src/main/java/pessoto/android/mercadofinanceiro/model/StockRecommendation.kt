package br.com.pessoto.mercadofinanceiro.model

import com.google.gson.annotations.SerializedName

class StockRecommendation(
    @SerializedName("id")
    var id: Int,

    @SerializedName("symbol")
    var symbol: String,

    @SerializedName("curent_price")
    var currentPrice: String,

    @SerializedName("target_price")
    var targetPrice: String,

    @SerializedName("potential_price")
    var potentialPrice: String,

    @SerializedName("recomendation")
    var recomendation: String,

    @SerializedName("date")
    var date: String,

    @SerializedName("link")
    var link: String,

    @SerializedName("symbol_image_url")
    var imageUrl: String,

    @SerializedName("company_name")
    var companyName: String
)
