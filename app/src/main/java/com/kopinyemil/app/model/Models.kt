package com.kopinyemil.app.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val emoji: String
)

data class CartItem(val product: Product, val quantity: Int) {
    val subtotal: Int get() = product.price * quantity
}

data class User(val username: String, val name: String)

data class Order(
    val customerName: String,
    val address: String,
    val addressDetail: String,
    val items: List<CartItem>
) {
    val total: Int get() = items.sumOf { it.subtotal }
}
