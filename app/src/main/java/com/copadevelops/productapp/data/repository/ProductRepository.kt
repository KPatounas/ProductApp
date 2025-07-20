package com.copadevelops.productapp.data.repository

import com.copadevelops.productapp.data.model.Product
import com.copadevelops.productapp.data.model.toDomain
import com.copadevelops.productapp.data.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository {

    private val api = RetrofitInstance.api

    suspend fun getProducts(): Result<List<Product>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getProducts()
                println("DEBUG: Response success = ${response.isSuccessful}")
                if (response.isSuccessful) {
                    val productsDto = response.body()?.products ?: emptyList()
                    val products = productsDto.mapNotNull { it.toDomain() }
                    println("DEBUG: Products size = ${products.size}")
                    Result.success(products)
                } else {
                    println("DEBUG: Response code = ${response.code()}")
                    Result.failure(Exception("Failed to fetch products: ${response.code()}"))
                }
            } catch (e: Exception) {
                println("DEBUG: Exception = ${e.message}")
                Result.failure(e)
            }
        }
    }

    suspend fun getProductById(id: Int): Result<Product> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getProductById(id)
                if (response.isSuccessful) {
                    val productDto = response.body()
                    val product = productDto?.toDomain()
                    if (product != null) {
                        Result.success(product)
                    } else {
                        Result.failure(Exception("Empty or invalid product data"))
                    }
                } else {
                    Result.failure(Exception("Failed to fetch product: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
