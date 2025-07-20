package com.copadevelops.productapp.data.model

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)
