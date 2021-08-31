package pessoto.android.mercadofinanceiro.feature.recommendation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.pessoto.mercadofinanceiro.databinding.AdapterRecommendationBinding
import br.com.pessoto.mercadofinanceiro.model.StockRecommendation

class RecommendationAdapter(    private val onClick: (StockRecommendation) -> Unit
) : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    private var stockList = listOf<StockRecommendation>()

    inner class ViewHolder(private val binding: AdapterRecommendationBinding, val onClick: (StockRecommendation) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stock: StockRecommendation, position: Int){
            binding.root.setOnClickListener {
                onClick(stock)
            }

            binding.apply {
                txtTicker.text = stock.companyName
            }
        }
    }

    fun updateList(listOfTodos: List<StockRecommendation>) {
        stockList = listOfTodos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stock = stockList[position]
        holder.bind(stock, position)
    }

    override fun getItemCount(): Int {
        return stockList.size
    }
}