package br.com.pessoto.mercadofinanceiro.feature.recommendation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.pessoto.mercadofinanceiro.data.repository.RecommendationRepository
import br.com.pessoto.mercadofinanceiro.data.repository.RecommendationRepositoryImpl
import br.com.pessoto.mercadofinanceiro.databinding.ActivityRecommendationBinding
import br.com.pessoto.mercadofinanceiro.feature.recommendation.viewModel.RecommendationViewModel
import br.com.pessoto.mercadofinanceiro.model.StateView
import br.com.pessoto.mercadofinanceiro.model.StockRecommendation
import pessoto.android.mercadofinanceiro.feature.recommendation.view.RecommendationAdapter


class RecommendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendationBinding

    private val repository: RecommendationRepository by lazy {
        RecommendationRepositoryImpl()
    }

    private val viewModel: RecommendationViewModel by lazy {
        RecommendationViewModel(repository)
    }

    private lateinit var recommendationAdapter: RecommendationAdapter

    private val observer = Observer<StateView<List<StockRecommendation>>> { stateView ->
        when (stateView) {
            is StateView.Loading -> {
                Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
            }
            is StateView.DataLoaded -> {
                Toast.makeText(this, "Carregou", Toast.LENGTH_SHORT).show()
                binding.progressBarRecommendation.visibility = View.GONE
                recommendationAdapter.updateList(stateView.data)

            }
            is StateView.Error -> {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = "Recomendação"

        recommendationAdapter = RecommendationAdapter { stock ->
            Toast.makeText(this, stock.companyName, Toast.LENGTH_LONG).show()
        }

        binding.rcRecommendation.apply {
            adapter = recommendationAdapter
            layoutManager = LinearLayoutManager(this@RecommendationActivity)
        }

    }

    override fun onResume() {
        super.onResume()
        binding.progressBarRecommendation.visibility = View.VISIBLE
        viewModel.stateView.observe(this, observer)
        viewModel.getRecommendation()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stateView.removeObserver(observer)
    }

}