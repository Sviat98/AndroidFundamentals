package com.bashkevich.androidfundamentals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.recyclerview.widget.RecyclerView
import com.bashkevich.androidfundamentals.model.Movie
import com.bumptech.glide.Glide

class MoviesListAdapter : RecyclerView.Adapter<MoviesListViewHolder>() {

    private var movies = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie,parent,false)
        return MoviesListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {

        val movie = movies[position]

        Glide.with(holder.itemView.context).load(movie.posterImageId)
            .into(holder.posterView)

        holder.ageCategory.text = "${movie.minAge} +"

        holder.title.text = movie.title

        holder.genres.text =movie.genres

        holder.rating.rating= movie.rating?.toFloat()!!

        holder.reviews.text = "${movie.reviews} reviews"

        holder.duration.text = "${movie.duration} min"
    }

    fun bindMovies( newMovies : List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int =movies.size
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