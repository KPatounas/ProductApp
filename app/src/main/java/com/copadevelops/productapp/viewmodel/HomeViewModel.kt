package com.copadevelops.productapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.copadevelops.productapp.data.model.Product
import com.copadevelops.productapp.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val products: List<Product>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel : ViewModel() {
    private val repository = ProductRepository()
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchProducts()
    }


    fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            val result = repository.getProducts()
            result.fold(
                onSuccess = { products ->
                    _uiState.value = HomeUiState.Success(products)
                },
                onFailure = { error ->
                    _uiState.value = HomeUiState.Error("Error: ${error.localizedMessage}")
                }
            )
        }
    }
}
