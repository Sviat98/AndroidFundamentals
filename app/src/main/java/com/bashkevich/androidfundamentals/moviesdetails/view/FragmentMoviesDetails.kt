package com.bashkevich.androidfundamentals.moviesdetails.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bashkevich.androidfundamentals.R
import com.bashkevich.androidfundamentals.databinding.FragmentMoviesDetailsBinding
import com.bashkevich.androidfundamentals.databinding.FragmentMoviesListBinding
import com.bashkevich.androidfundamentals.model.viewobject.Actor
import com.bashkevich.androidfundamentals.moviesdetails.viewmodel.MoviesDetailsViewModel
import com.bashkevich.androidfundamentals.moviesdetails.viewmodel.MoviesDetailsViewModelFactory
import com.google.android.material.transition.MaterialContainerTransform


class FragmentMoviesDetails : Fragment() {
    private var actorsRecyclerView: RecyclerView? = null
    private var movieId: Int? = null

    private val actorsAdapter = ActorsAdapter()

    private var _binding: FragmentMoviesDetailsBinding? = null

    private val binding get() = _binding!!

    private val moviesDetailsViewModel: MoviesDetailsViewModel by viewModels {
        MoviesDetailsViewModelFactory(
            requireContext().applicationContext
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(PARAM_MOVIE_ID)
        }

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.main_container
            duration = 1_000
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actorsRecyclerView = view.findViewById(R.id.actors_recycler_view)

        setUpActorsRecyclerView()

        moviesDetailsViewModel.movieLiveData.observe(this.viewLifecycleOwner) { selectedMovie ->
            selectedMovie?.let { movie ->

                binding.backdropDetails.load(movie.backdrop) {
                    crossfade(true)
                }

                binding.ageCategory.text =
                    context?.getString(R.string.age_category, movie.minimumAge)
                binding.titleDetails.text = movie.title
                binding.genresDetails.text = movie.genres?.joinToString()
                binding.movieRatingDetails.rating = movie.ratings / 2
                binding.reviewsDetails.text =
                    context?.getString(R.string.reviews, movie.numberOfRatings)
                binding.descriptionDetails.text = movie.overview

                val actors = movie.actors

                if (actors != null) {
                    if (actors.isEmpty()) {
                        binding.cast.visibility = View.GONE
                    } else {
                        setUpActors(actors)
                    }
                }

            }
        }
        movieId?.let { moviesDetailsViewModel.loadMovie(it) }
    }

    private fun setUpActors(actors: List<Actor>) {
        actorsAdapter.bindActors(actors)
    }

    private fun setUpActorsRecyclerView() {
        binding.actorsRecyclerView.adapter = actorsAdapter

        binding.actorsRecyclerView.addItemDecoration(ActorsDecoration())
        binding.actorsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PARAM_MOVIE_ID = "movieId"

        fun newInstance(
            movieId: Int
        ): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val args = Bundle()
            args.putInt(PARAM_MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }
}