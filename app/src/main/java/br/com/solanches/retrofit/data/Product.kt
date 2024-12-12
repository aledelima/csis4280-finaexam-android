package br.com.solanches.retrofit.data

data class Product(
    var imageUrl: String,
    var name: String,
    var price: Double,
    var quantityAvailable: Int,
    var quantityToPurchase: Int
)
