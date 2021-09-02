package pessoto.android.mercadofinanceiro.feature.recommendation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.pessoto.mercadofinanceiro.databinding.ActivityRecommendationBinding
import pessoto.android.mercadofinanceiro.data.repository.RecommendationRepository
import pessoto.android.mercadofinanceiro.data.repository.RecommendationRepositoryImpl
import pessoto.android.mercadofinanceiro.feature.recommendation.viewModel.RecommendationViewModel
import pessoto.android.mercadofinanceiro.model.StateView
import pessoto.android.mercadofinanceiro.model.StockRecommendation
import pessoto.android.mercadofinanceiro.util.view.BaseActivity
import pessoto.android.mercadofinanceiro.util.view.Dialogs
import pessoto.android.mercadofinanceiro.util.view.DialogsCallback
import java.net.UnknownHostException


class RecommendationActivity : BaseActivity() {

    private lateinit var binding: ActivityRecommendationBinding

    private var listRecommendation = listOf<StockRecommendation>()

    private val titleDialog = "Erro inesperado"
    private var messageDialog = "Ocorreu um erro ao recuperar os dados!"
    private val positiveDialog = "Tentar novamente"
    private val negativeDialog = "Fechar"

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
                Dialogs.cancelDialog()
            }
            is StateView.Error -> {
                binding.progressBarRecommendation.visibility = View.GONE

                if (listRecommendation.isEmpty()) {
                    if (stateView.e is UnknownHostException) {
                        messageDialog = "Sem conexão com a internet!"
                        showDialog()
                    } else {
                        showDialog()
                    }
                }
            }
        }
    }

    private fun showDialog() = Dialogs.showDialog(context = this, title = titleDialog, message = messageDialog, positiveButton = positiveDialog, negativeButton = negativeDialog, cancelable = false, callback = callbackDialog)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

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