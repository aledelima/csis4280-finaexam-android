package br.com.solanches.retrofit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.solanches.retrofit.api.RetrofitInstance
import br.com.solanches.retrofit.data.Product
import br.com.solanches.retrofit.data.Purchase
import br.com.solanches.retrofit.data.PurchaseItem
import br.com.solanches.retrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    private var products: List<Product> = emptyList() // Store fetched products

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(this)

        // Fetch products from the backend
        fetchProducts()

        // Handle Compute Total button click
        binding.buttonComputeTotal.setOnClickListener {
            computeTotal()
        }

        // Handle Purchase button click
        binding.buttonPurchase.setOnClickListener {
            purchaseProducts()
        }
    }

    private fun fetchProducts() {
        lifecycleScope.launch {
            try {
                // Fetch products from the backend
                products = RetrofitInstance.api.getProducts()
                productAdapter = ProductAdapter(products)
                binding.recyclerViewProducts.adapter = productAdapter
                Toast.makeText(
                    this@MainActivity,
                    "Products loaded successfully!",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                e.printStackTrace() // Log detailed stack trace
                Toast.makeText(
                    this@MainActivity,
                    "Error fetching products: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun computeTotal() {
        // Filter products with quantityToPurchase > 0
        val validProducts = products.filter { it.quantityToPurchase > 0 }

        // Check if there are any valid products
        if (validProducts.isEmpty()) {
            Toast.makeText(this, "No products selected for calculation.", Toast.LENGTH_SHORT).show()
            return
        }

        // Calculate the total price
        val total = validProducts.sumOf { it.quantityToPurchase * it.price }
        val totalWithTax = total + (total * 0.12) // Add 12% tax

        // Update the total price TextView
        binding.textViewTotalPrice.text =
            "Total Price: $${"%.2f".format(totalWithTax)} (Includes 12% Tax)"
    }

    private fun purchaseProducts() {
        val clientName = binding.editTextClientName.text.toString().trim()

        // Validate client name
        if (clientName.isEmpty()) {
            Toast.makeText(this, "Please enter a client name.", Toast.LENGTH_SHORT).show()
            return
        }

        // Filter products with a quantity to purchase > 0
        val purchasedProducts = products.filter { it.quantityToPurchase > 0 }
        if (purchasedProducts.isEmpty()) {
            Toast.makeText(this, "No products selected for purchase.", Toast.LENGTH_SHORT).show()
            return
        }

        // Create a list of PurchaseItem
        val items = purchasedProducts.map {
            PurchaseItem(
                productName = it.name,
                quantity = it.quantityToPurchase,
                price = it.price
            )
        }

        // Calculate the total price
        val totalPrice = items.sumOf { it.quantity * it.price }

        // Create the Purchase object
        val purchase = Purchase(
            customerName = clientName,
            items = items,
            totalPrice = totalPrice
        )

        // Send purchase to the backend
        lifecycleScope.launch {
            try {
                RetrofitInstance.api.createPurchase(purchase)
                Toast.makeText(this@MainActivity, "Purchase successful!", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace() // Log error for debugging
                Toast.makeText(
                    this@MainActivity,
                    "Error processing purchase: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}