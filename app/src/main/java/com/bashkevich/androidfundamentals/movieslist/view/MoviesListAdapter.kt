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
import com.bashkevich.androidfundamentals.databinding.ViewHolderMovieBinding
import com.bashkevich.androidfundamentals.model.viewobject.Movie

class MoviesListAdapter(private val onMovieClickListener: OnMovieClickListener) :
    RecyclerView.Adapter<MoviesListViewHolder>() {

    private var movies = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val binding =
            ViewHolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {

        val movie = movies[position]

        holder.binding.moviePoster.load(movie.poster) {
            crossfade(true)
        }

        holder.itemView.transitionName =
            "${holder.itemView.context.getString(R.string.movie_transition)}${movie.id}"

        holder.binding.movieAgeCategory.text =
            holder.context.getString(R.string.age_category, movie.minimumAge)

        holder.binding.movieTitle.text = movie.title

        holder.binding.movieGenres.text = movie.genres?.joinToString()

        holder.binding.movieRating.rating = movie.ratings / 2

        holder.binding.movieReviews.text =
            holder.context.getString(R.string.reviews, movie.numberOfRatings)

        holder.binding.movieDuration.text =
            holder.context.getString(R.string.duration, movie.runtime)

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

class MoviesListViewHolder(val binding: ViewHolderMovieBinding) :
    RecyclerView.ViewHolder(binding.root)

interface OnMovieClickListener {
    fun onMovieClick(movieId: Int, view: View)
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context