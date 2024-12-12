package br.com.solanches.retrofit.data

data class Purchase(
    val customerName: String,
    val items: List<PurchaseItem>,
    val totalPrice: Double
)
