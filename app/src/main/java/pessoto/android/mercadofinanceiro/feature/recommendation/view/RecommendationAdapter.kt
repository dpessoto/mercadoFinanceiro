package pessoto.android.mercadofinanceiro.feature.recommendation.view

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
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
                txtValue.text = stock.currentPrice
                txtRecommendation.text = stock.recommendation
                txtRecommendation.setBackgroundResource(stock.getRecommendationDrawble())

                txtCompanyName.setOnLongClickListener {
                    Toast.makeText(it.context, stock.companyName, Toast.LENGTH_SHORT).show()
                    true
                }
                
                Picasso.get().load(stock.imageUrl).into(imageStock, object : Callback {
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                        imageStock.visibility = View.VISIBLE
                        val imageBitmap = (imageStock.drawable as BitmapDrawable).bitmap
                        val imageDrawable =
                            RoundedBitmapDrawableFactory.create(imageStock.resources, imageBitmap)
                        imageDrawable.isCircular = true
                        imageDrawable.cornerRadius =
                            max(imageBitmap.width, imageBitmap.height) / 8.0f
                        imageStock.setImageDrawable(imageDrawable)
                    }

                    override fun onError(e: Exception?) {

                    }
                })
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
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