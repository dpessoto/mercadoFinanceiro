package pessoto.android.mercadofinanceiro.feature.recommendation.view

import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.recyclerview.widget.RecyclerView
import br.com.pessoto.mercadofinanceiro.databinding.AdapterRecommendationBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import pessoto.android.mercadofinanceiro.model.StockRecommendation
import kotlin.math.max

class RecommendationAdapter(
    private val onClick: (StockRecommendation) -> Unit
) : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    private var stockList = listOf<StockRecommendation>()

    inner class ViewHolder(
        private val binding: AdapterRecommendationBinding,
        val onClick: (StockRecommendation) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stock: StockRecommendation, position: Int) {
            binding.root.setOnClickListener {
                onClick(stock)
            }

            binding.apply {
                txtTicker.text = stock.symbol
                txtCompanyName.text = stock.companyName
                txtCompanyName.setOnLongClickListener {
                    Toast.makeText(it.context, stock.companyName, Toast.LENGTH_SHORT).show()
                    true
                }
                
                Picasso.get().load(stock.imageUrl).into(imageView, object : Callback {
                    override fun onSuccess() {
                        val imageBitmap = (imageView.drawable as BitmapDrawable).bitmap
                        val imageDrawable =
                            RoundedBitmapDrawableFactory.create(imageView.resources, imageBitmap)
                        imageDrawable.isCircular = true
                        imageDrawable.cornerRadius =
                            max(imageBitmap.width, imageBitmap.height) / 8.0f
                        imageView.setImageDrawable(imageDrawable)
                    }

                    override fun onError(e: Exception?) {

                    }
                })
            }
        }
    }

    fun updateList(listOfTodos: List<StockRecommendation>) {
        stockList = listOfTodos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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