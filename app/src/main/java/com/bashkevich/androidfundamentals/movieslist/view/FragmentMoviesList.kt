package com.bashkevich.androidfundamentals.movieslist.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashkevich.androidfundamentals.*
import com.bashkevich.androidfundamentals.model.viewobject.Movie
import com.bashkevich.androidfundamentals.moviesdetails.view.FragmentMoviesDetails
import com.bashkevich.androidfundamentals.movieslist.viewmodel.MoviesListViewModel
import com.bashkevich.androidfundamentals.movieslist.viewmodel.MoviesListViewModelFactory


class FragmentMoviesList : Fragment() {
    private var recyclerView: RecyclerView? = null

    private val moviesListViewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(
            requireContext().applicationContext
        )
    }

    private val onMovieClickListener = object : OnMovieClickListener {
        override fun onMovieClick(movieId: Int) {
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                ?.add(R.id.main_container, FragmentMoviesDetails.newInstance(movieId))?.commit()
        }
    }

    private val moviesListAdapter = MoviesListAdapter(onMovieClickListener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        recyclerView = view.findViewById(R.id.movies_recycler_view)

        setUpMoviesListRecyclerView()
        moviesListViewModel.loadMoviesList()

        moviesListViewModel.moviesListLiveData.observe(this.viewLifecycleOwner) { movies ->
            setUpMoviesList(movies)
        }

        return view
    }

    private fun setUpMoviesListRecyclerView() {
        val spanCount =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                3
            } else {
                2
            }
        recyclerView?.layoutManager = GridLayoutManager(context, spanCount)
        recyclerView?.adapter = moviesListAdapter
        recyclerView?.addItemDecoration(MoviesDecoration())
    }

    private fun setUpMoviesList(movies: List<Movie>) {
        moviesListAdapter.bindMovies(movies)
    }


    override fun onDetach() {
        super.onDetach()
        recyclerView = null
    }
}