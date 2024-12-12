package br.com.solanches.retrofit.data

import com.google.gson.annotations.SerializedName

data class Purchase(
    @SerializedName("customer_name") val customerName: String, // Maps to "customer_name"
    @SerializedName("items") val items: List<PurchaseItem>,    // Maps to "items"
    @SerializedName("total_price") val totalPrice: Double      // Maps to "total_price"
)
