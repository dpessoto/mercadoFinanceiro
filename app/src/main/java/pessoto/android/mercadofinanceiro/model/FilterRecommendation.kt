package pessoto.android.mercadofinanceiro.model

class FilterRecommendation(var clicked: Boolean = false, var filter: Filter = Filter.ALL) {

    enum class Filter(val value: String) {
        ALL("Todos"),
        BUY("Compra"),
        SELl("Venda"),
        NEUTRAL("Neutro"),
        RESTRICTED("Restrito")
    }
}