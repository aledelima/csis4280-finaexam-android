package br.com.solanches.retrofit.data

import com.google.gson.annotations.SerializedName

data class PurchaseItem(
    @SerializedName("product_name") val productName: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("price") val price: Double
)