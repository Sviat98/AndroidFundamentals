package com.bashkevich.androidfundamentals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashkevich.androidfundamentals.data.MoviesList

class FragmentMoviesList : Fragment() {
    private var recyclerView: RecyclerView? = null




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {

        val view  = inflater.inflate(R.layout.fragment_movies_list, container, false)

        recyclerView = view.findViewById(R.id.movies_recycler_view)

        recyclerView?.layoutManager = GridLayoutManager(view.context,2)

        recyclerView?.adapter = MoviesListAdapter().apply {
            bindMovies(MoviesList().getMovies())
        }
//        val posterView =  view.findViewById<ImageView>(R.id.movie_poster)
//        posterView.setOnClickListener{
//                        activity?.let{
//                            it.supportFragmentManager.commit {
//                                addToBackStack(null)
//                                add<FragmentMoviesDetails>(R.id.main_container)
//                            }
//                        }
//        }

        return view
    }


}