package pessoto.android.mercadofinanceiro.feature.recommendation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pessoto.android.mercadofinanceiro.data.repository.RecommendationRepository
import pessoto.android.mercadofinanceiro.data.repository.ResultRepository
import pessoto.android.mercadofinanceiro.model.StateView
import pessoto.android.mercadofinanceiro.model.StockRecommendation
import kotlinx.coroutines.launch

class RecommendationViewModel(private val repository: RecommendationRepository) : ViewModel() {

    private val _stateView = MutableLiveData<StateView<List<StockRecommendation>>>()
    val stateView: LiveData<StateView<List<StockRecommendation>>>
        get() = _stateView

    fun getRecommendation() {
//        if (_stateView.value != null) return

        viewModelScope.launch {
            _stateView.value = StateView.Loading

            when (val result = repository.getAllRecommendation()) {
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