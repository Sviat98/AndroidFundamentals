package com.bashkevich.androidfundamentals

import android.media.browse.MediaBrowser
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bashkevich.androidfundamentals.data.DataList
import com.bashkevich.androidfundamentals.model.Movie

class FragmentMoviesList : Fragment() {
    private var recyclerView: RecyclerView? = null

    private val onMovieClickListener =object : OnMovieClickListener {
        override fun onMovieClick(movie: Movie) {
            if (movie.title?.equals(getString(R.string.title))!!)
            activity?.let {
                it.supportFragmentManager.commit {
                    addToBackStack(null)
                    add<FragmentMoviesDetails>(R.id.main_container)
                }
            }else{
                Toast.makeText(context,"Not implemented!",Toast.LENGTH_LONG).show()
            }

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {

        val view  = inflater.inflate(R.layout.fragment_movies_list, container, false)

       recyclerView = view.findViewById(R.id.movies_recycler_view)

        recyclerView?.layoutManager = GridLayoutManager(view.context,2)

        recyclerView?.adapter = MoviesListAdapter(onMovieClickListener).apply {
           bindMovies(DataList().getMovies())
        }

        recyclerView?.addItemDecoration(MoviesDecoration())

        return view
    }

    override fun onDetach() {
        super.onDetach()
        recyclerView =null
    }



}