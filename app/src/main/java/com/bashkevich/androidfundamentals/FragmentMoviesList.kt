package com.bashkevich.androidfundamentals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashkevich.androidfundamentals.data.loadMovies
import com.bashkevich.androidfundamentals.data.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FragmentMoviesList : Fragment() {
    private var recyclerView: RecyclerView? = null
    private val scope = CoroutineScope(Dispatchers.Main)

    private val onMovieClickListener = object : OnMovieClickListener {
        override fun onMovieClick(movie: Movie) {
            activity?.let {
                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .add(R.id.main_container, FragmentMoviesDetails.newInstance(movie)).commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        recyclerView = view.findViewById(R.id.movies_recycler_view)

        recyclerView?.layoutManager = GridLayoutManager(view.context, 2)

        recyclerView?.adapter = MoviesListAdapter(onMovieClickListener).apply {
            scope.launch {
                val movies = context?.let { loadMovies(it) }
                if (movies != null) {
                    bindMovies(movies)
                }
            }
        }

        recyclerView?.addItemDecoration(MoviesDecoration())

        return view
    }


    override fun onDetach() {
        super.onDetach()
        recyclerView = null
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}