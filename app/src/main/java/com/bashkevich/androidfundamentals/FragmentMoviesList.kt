package com.bashkevich.androidfundamentals

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashkevich.androidfundamentals.data.Movie


class FragmentMoviesList : Fragment() {
    private var recyclerView: RecyclerView? = null

    private val moviesListViewModel: MoviesListViewModel by viewModels { MoviesListViewModelFactory() }

    private val onMovieClickListener = object : OnMovieClickListener {
        override fun onMovieClick(movie: Movie) {
            activity?.let {
                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .add(R.id.main_container, FragmentMoviesDetails.newInstance(movie)).commit()
            }
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

        moviesListViewModel.loadMoviesList()


        moviesListViewModel.moviesListLiveData.observe(this.viewLifecycleOwner){movies->
            setUpMoviesList(movies)
        }

        setUpMoviesListRecyclerView()


        return view
    }

    private fun setUpMoviesListRecyclerView(){
        recyclerView?.layoutManager = GridLayoutManager(context, 2)
        recyclerView?.adapter = moviesListAdapter
        recyclerView?.addItemDecoration(MoviesDecoration())
    }

    private fun setUpMoviesList(movies : List<Movie>){
        moviesListAdapter.bindMovies(movies)
        for (movie in movies){
            Log.d("moviesList rating of ${movie.title}",movie.ratings.div(2).toString())
        }
    }


    override fun onDetach() {
        super.onDetach()
        recyclerView = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}