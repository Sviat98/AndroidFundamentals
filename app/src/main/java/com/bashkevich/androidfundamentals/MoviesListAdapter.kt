package com.bashkevich.androidfundamentals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.recyclerview.widget.RecyclerView
import com.bashkevich.androidfundamentals.model.Movie

class MoviesListAdapter(private val onMovieClickListener: OnMovieClickListener) :
    RecyclerView.Adapter<MoviesListViewHolder>() {

    private var movies = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        return MoviesListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {

        val movie = movies[position]

        movie.posterImageId.let { holder.posterView.setImageResource(it) }

        holder.ageCategory.text = holder.context.getString(R.string.age_category, movie.minAge)

        holder.title.text = movie.title

        holder.genres.text = movie.genres

        holder.rating.rating = movie.rating.toFloat()

        holder.reviews.text = holder.context.getString(R.string.reviews, movie.reviews)

        holder.duration.text = holder.context.getString(R.string.duration, movie.duration)

        holder.itemView.setOnClickListener {
            onMovieClickListener.onMovieClick(movie)
        }
    }

    fun bindMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int = movies.size
}

class MoviesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val posterView = itemView.findViewById<ImageView>(R.id.movie_poster)
    val ageCategory = itemView.findViewById<TextView>(R.id.age_category)
    val title = itemView.findViewById<TextView>(R.id.title)
    val genres = itemView.findViewById<TextView>(R.id.genres)
    val rating = itemView.findViewById<AppCompatRatingBar>(R.id.movie_rating)
    val reviews = itemView.findViewById<TextView>(R.id.reviews)
    val duration = itemView.findViewById<TextView>(R.id.duration)


}

interface OnMovieClickListener {
    fun onMovieClick(movie: Movie)
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context