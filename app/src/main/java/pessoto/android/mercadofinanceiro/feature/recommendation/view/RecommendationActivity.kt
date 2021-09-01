package pessoto.android.mercadofinanceiro.feature.recommendation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import pessoto.android.mercadofinanceiro.data.repository.RecommendationRepository
import br.com.pessoto.mercadofinanceiro.databinding.ActivityRecommendationBinding
import pessoto.android.mercadofinanceiro.feature.recommendation.viewModel.RecommendationViewModel
import pessoto.android.mercadofinanceiro.model.StateView
import pessoto.android.mercadofinanceiro.model.StockRecommendation
import pessoto.android.mercadofinanceiro.data.repository.RecommendationRepositoryImpl
import pessoto.android.mercadofinanceiro.util.Dialogs
import pessoto.android.mercadofinanceiro.util.DialogsCallback
import java.net.UnknownHostException


class RecommendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendationBinding

    private var listRecommendation = listOf<StockRecommendation>()

    private val repository: RecommendationRepository by lazy {
        RecommendationRepositoryImpl()
    }

    private val viewModel: RecommendationViewModel by lazy {
        RecommendationViewModel(repository)
    }

    private val recommendationAdapter by lazy {
        RecommendationAdapter { stock ->
            Toast.makeText(this, stock.symbol, Toast.LENGTH_LONG).show()
        }
    }

    private val callbackDialog = object :
        DialogsCallback {
        override fun callbackPositiveClick() {
            viewModel.getRecommendation()
        }

        override fun callbackNegativeClick() {
            finish()
        }
    }

    private val observer = Observer<StateView<List<StockRecommendation>>> { stateView ->
        when (stateView) {
            is StateView.Loading -> {
                binding.progressBarRecommendation.visibility = View.VISIBLE
            }
            is StateView.DataLoaded -> {
                binding.progressBarRecommendation.visibility = View.GONE
                listRecommendation = stateView.data
                recommendationAdapter.updateList(listRecommendation)
            }
            is StateView.Error -> {
                binding.progressBarRecommendation.visibility = View.GONE

                if (listRecommendation.isEmpty()) {
                    val title = "Erro inesperado"
                    var message = "Ocorreu um erro ao recuperar os dados!"
                    val positive = "Tentar novamente"
                    val negative = "Fechar"

                    if (stateView.e is UnknownHostException) {
                        message = "Sem conexão com a internet!"
                        Dialogs.showDialog(
                            this,
                            title,
                            message,
                            positive,
                            negative,
                            false,
                            callbackDialog
                        )
                    } else {
                        Dialogs.showDialog(
                            this,
                            title,
                            message,
                            positive,
                            negative,
                            false,
                            callbackDialog
                        )
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = "Recomendação"

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
        Dialogs.cancelDialog()
    }

}