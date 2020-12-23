package com.bashkevich.androidfundamentals

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashkevich.androidfundamentals.data.Movie
import com.squareup.picasso.Picasso


class FragmentMoviesDetails : Fragment() {
    private var actorsRecyclerView: RecyclerView? = null
    private var movie: Movie? = null

    private val actorsAdapter = ActorsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelable(PARAM_MOVIE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)

        val backdropView = view.findViewById<ImageView>(R.id.backdrop_details)
        val ageCategoryView = view.findViewById<TextView>(R.id.age_category)
        val titleView = view.findViewById<TextView>(R.id.title_details)
        val genresView = view.findViewById<TextView>(R.id.genres_details)
        val ratingView = view.findViewById<RatingBar>(R.id.movie_rating_details)
        val reviewView = view.findViewById<TextView>(R.id.reviews_details)
        val descriptionView = view.findViewById<TextView>(R.id.description_details)
        val castView = view.findViewById<TextView>(R.id.cast)
        actorsRecyclerView = view.findViewById(R.id.actors_recycler_view)


        movie?.let { movie ->
            Picasso.get().load(Uri.parse(movie.backdrop)).into(backdropView)

            ageCategoryView.text = context?.getString(R.string.age_category, movie?.minimumAge)
            titleView.text = movie.title
            genresView.text = movie.genres.joinToString { it.name }
            //ratingView.rating = movie.ratings.div(2)
            ratingView.rating = movie.ratings/2
            reviewView.text = context?.getString(R.string.reviews, movie?.numberOfRatings)
            descriptionView.text = movie.overview

            setUpActorsRecyclerView()

            val actors = movie.actors

            if (actors.isEmpty()) {
                castView.visibility = View.GONE
            }

            actorsAdapter.bindActors(actors)


            Log.d("moviesDs rating of ${movie.title}","${movie.ratings.div(2)}")

        }
        return view

    }

    private fun setUpActorsRecyclerView() {
        actorsRecyclerView?.adapter = actorsAdapter

        actorsRecyclerView?.addItemDecoration(ActorsDecoration())
        actorsRecyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDetach() {
        super.onDetach()
        actorsRecyclerView = null
    }

    companion object {
        private const val PARAM_ID = "movie_id"
        private const val PARAM_MOVIE = "movie"

        fun newInstance(
            movie: Movie,
        ): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val args = Bundle()
            args.putParcelable(PARAM_MOVIE, movie)
            fragment.arguments = args
            return fragment
        }
    }
}