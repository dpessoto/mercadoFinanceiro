package pessoto.android.mercadofinanceiro.feature.recommendation.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.pessoto.mercadofinanceiro.databinding.AdapterFilterRecommendationBinding
import pessoto.android.mercadofinanceiro.model.FilterRecommendation

class FilterRecommendationAdapter(
    private val onClick: (FilterRecommendation.Filter) -> Unit
) : RecyclerView.Adapter<FilterRecommendationAdapter.ViewHolder>() {

    private val filterList = getList()

    inner class ViewHolder(
        private val binding: AdapterFilterRecommendationBinding,
        val onClick: (FilterRecommendation.Filter) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bind(filter: FilterRecommendation) {
            binding.root.setOnClickListener {
                if (!filter.clicked) {
                    onClick(filter.filter)
                    filterList
                    for (item in filterList) {
                        item.clicked = item.filter == filter.filter
                    }
                    notifyDataSetChanged()
                }
            }

            binding.apply {
                if (filter.clicked) {
                    cardFilter.setCardBackgroundColor(Color.parseColor("#9a8073"))
                    txtLabel.setTextColor(Color.WHITE)
                } else {
                    cardFilter.setCardBackgroundColor(Color.parseColor("#272B34"))
                    txtLabel.setTextColor(Color.parseColor("#767A83"))
                }
                txtLabel.text = filter.filter.value
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterFilterRecommendationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stock = filterList[position]
        holder.bind(stock)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    private fun getList():ArrayList<FilterRecommendation> {
        val arrayList = ArrayList<FilterRecommendation>()
        arrayList.add(FilterRecommendation(clicked = true))
        arrayList.add(FilterRecommendation(filter = FilterRecommendation.Filter.BUY))
        arrayList.add(FilterRecommendation(filter = FilterRecommendation.Filter.SELl))
        arrayList.add(FilterRecommendation(filter = FilterRecommendation.Filter.NEUTRAL))
        arrayList.add(FilterRecommendation(filter = FilterRecommendation.Filter.RESTRICTED))
        return  arrayList
    }
}