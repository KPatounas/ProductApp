package com.copadevelops.productapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.copadevelops.productapp.data.model.Product
import com.copadevelops.productapp.data.repository.ProductRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    var product by mutableStateOf<Product?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun fetchProduct(id: Int) {
        isLoading = true
        error = null
        viewModelScope.launch {
            val result = repository.getProductById(id)
            result.fold(
                onSuccess = {
                    product = it
                    isLoading = false
                },
                onFailure = {
                    error = it.message
                    isLoading = false
                }
            )
        }
    }
}
