package com.bashkevich.androidfundamentals.movieslist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bashkevich.androidfundamentals.R
import com.bashkevich.androidfundamentals.model.viewobject.Movie

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

        holder.posterView.load(movie.poster) {
            crossfade(true)
        }

        holder.itemView.transitionName =
            "${holder.itemView.context.getString(R.string.movie_transition)}${movie.id}"

        holder.ageCategory.text = holder.context.getString(R.string.age_category, movie.minimumAge)

        holder.title.text = movie.title

        holder.genres.text = movie.genres?.joinToString()

        holder.rating.rating = movie.ratings / 2

        holder.reviews.text = holder.context.getString(R.string.reviews, movie.numberOfRatings)

        holder.duration.text = holder.context.getString(R.string.duration, movie.runtime)

        holder.itemView.setOnClickListener {
            if (movies.map { it.id }.contains(movie.id)) {
                onMovieClickListener.onMovieClick(movie.id, holder.itemView)
            } else {
                Toast.makeText(holder.context, "No such movie!", Toast.LENGTH_SHORT).show()
            }
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
    val ageCategory = itemView.findViewById<TextView>(R.id.movie_age_category)
    val title = itemView.findViewById<TextView>(R.id.movie_title)
    val genres = itemView.findViewById<TextView>(R.id.movie_genres)
    val rating = itemView.findViewById<AppCompatRatingBar>(R.id.movie_rating)
    val reviews = itemView.findViewById<TextView>(R.id.movie_reviews)
    val duration = itemView.findViewById<TextView>(R.id.movie_duration)

}

interface OnMovieClickListener {
    fun onMovieClick(movieId: Int, view: View)
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context