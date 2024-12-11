package br.com.solanches.retrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.solanches.retrofit.data.Movie
import br.com.solanches.retrofit.databinding.ItemMovieBinding
import com.bumptech.glide.Glide

class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        val binding = holder.binding

        binding.textViewTitle.text = movie.title
        binding.textViewDirector.text = "Director: ${movie.director}"
        binding.textViewYear.text = "Year: ${movie.year}"

        Glide.with(binding.imageViewPoster.context)
            .load(movie.poster)
            .placeholder(R.drawable.ic_placeholder) // Optional placeholder
            .error(R.drawable.ic_error) // Optional error image
            .into(binding.imageViewPoster)
    }

    override fun getItemCount(): Int = movies.size
}
