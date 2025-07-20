package com.copadevelops.productapp.data.model

fun ProductDto.toDomain(): Product? {
    val id = this.id ?: return null
    val title = this.title ?: return null
    val description = this.description ?: ""
    val price = this.price ?: 0.0
    val rating = this.rating ?: 0.0
    val brand = this.brand ?: "Unknown"
    val category = this.category ?: "Unknown"
    val thumbnail = this.thumbnail ?: ""
    val images = this.images ?: emptyList()

    return Product(id, title, description, price, rating, brand, category, thumbnail, images)
}