package br.com.solanches.retrofit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.solanches.retrofit.api.RetrofitInstance
import br.com.solanches.retrofit.databinding.ActivityMainBinding

import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewMovies.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            try {
                val movies = RetrofitInstance.api.getMovies()
                binding.recyclerViewMovies.adapter = MovieAdapter(movies)
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}