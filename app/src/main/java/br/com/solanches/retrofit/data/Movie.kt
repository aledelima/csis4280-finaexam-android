package br.com.solanches.retrofit.data

data class Movie(
    val id: Int,
    val title: String,
    val director: String,
    val year: Int,
    val poster: String // Add this field if not already present
)

