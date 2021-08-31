package br.com.pessoto.mercadofinanceiro.feature.recommendation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.pessoto.mercadofinanceiro.data.repository.RecommendationRepository
import br.com.pessoto.mercadofinanceiro.data.repository.ResultRepository
import br.com.pessoto.mercadofinanceiro.model.StateView
import br.com.pessoto.mercadofinanceiro.model.StockRecommendation
import kotlinx.coroutines.launch

class RecommendationViewModel(private val repository: RecommendationRepository) : ViewModel() {

    private val _stateView = MutableLiveData<StateView<List<StockRecommendation>>>()
    val stateView: LiveData<StateView<List<StockRecommendation>>>
        get() = _stateView

    fun getRecommendation() {
//        if (_stateView.value != null) return

        viewModelScope.launch {
            _stateView.value = StateView.Loading
            val result = try {
                repository.getRecommendation()
            } catch (e: Exception) {
                ResultRepository.Error(java.lang.Exception("error"))
            }

            when(result) {
                is ResultRepository.Success -> {
                    _stateView.value = StateView.DataLoaded(result.data)
                }
                is ResultRepository.Error -> {
                    _stateView.value =
                        StateView.Error(Exception("Ocorreu um erro ao recuperar os dados!"))
                }
            }
        }
    }

}