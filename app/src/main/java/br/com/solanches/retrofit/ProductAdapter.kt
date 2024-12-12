package br.com.solanches.retrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.solanches.retrofit.data.Product
import br.com.solanches.retrofit.databinding.ItemProductBinding
import com.bumptech.glide.Glide

class ProductAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // ViewHolder for the product item
    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        val binding = holder.binding

        // Bind product data to views
        binding.textViewProductName.text = product.name
        binding.textViewProductPrice.text = "Price: $${product.price}"
        binding.textViewProductQuantity.text = "Available: ${product.quantityAvailable}"

        // Load product image
        Glide.with(binding.imageViewProduct.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_placeholder) // Optional placeholder
            .error(R.drawable.ic_error) // Optional error image
            .into(binding.imageViewProduct)

        // Save user input for quantity to purchase
        binding.editTextQuantityToPurchase.setOnFocusChangeListener { _, _ ->
            product.quantityToPurchase = binding.editTextQuantityToPurchase.text.toString().toIntOrNull() ?: 0
        }
    }

    override fun getItemCount(): Int = products.size
}
