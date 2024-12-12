package br.com.solanches.retrofit.api

import br.com.solanches.retrofit.data.Product
import br.com.solanches.retrofit.data.Purchase
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApiService {

    // Retrieve all products
    @GET("products")
    suspend fun getProducts(): List<Product>

    // Create a new purchase
    @POST("purchase")
    suspend fun createPurchase(@Body purchase: Purchase)

    // Retrieve all purchases for a specific client
    @GET("purchases/{client_name}")
    suspend fun getPurchasesByClient(@Path("client_name") clientName: String): List<Purchase>
}
