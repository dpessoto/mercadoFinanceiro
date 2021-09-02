package pessoto.android.mercadofinanceiro.feature.recommendation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pessoto.android.mercadofinanceiro.data.repository.RecommendationRepository
import pessoto.android.mercadofinanceiro.data.repository.ResultRepository
import pessoto.android.mercadofinanceiro.model.FilterRecommendation.Filter
import pessoto.android.mercadofinanceiro.model.StateView
import pessoto.android.mercadofinanceiro.model.StockRecommendation

class RecommendationViewModel(private val repository: RecommendationRepository) : ViewModel() {

    private val _stateView = MutableLiveData<StateView<List<StockRecommendation>>>()
    val stateView: LiveData<StateView<List<StockRecommendation>>>
        get() = _stateView

    fun getRecommendation(filter: Filter) {
//        if (_stateView.value != null) return

        viewModelScope.launch {
            _stateView.value = StateView.Loading
            val result = when (filter) {
                Filter.ALL -> repository.getAllRecommendation()
                Filter.BUY -> repository.getBuyRecommendation()
                Filter.SELl -> repository.getSellRecommendation()
                Filter.NEUTRAL -> repository.getNeutralRecommendation()
                Filter.RESTRICTED -> repository.getRestrictedRecommendation()
            }

            when (result) {
                is ResultRepository.Success -> {
                    _stateView.value = StateView.DataLoaded(result.data)
                }
                is ResultRepository.Error -> {
                    _stateView.value = StateView.Error(result.exception)
                }
            }
        }
    }

}