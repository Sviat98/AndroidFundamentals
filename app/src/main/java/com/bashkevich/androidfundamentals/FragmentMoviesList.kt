package com.bashkevich.androidfundamentals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.add
import androidx.fragment.app.commit

class FragmentMoviesList : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {

        val view  = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val posterView =  view.findViewById<ImageView>(R.id.movie_poster)
        posterView.setOnClickListener{
                        activity?.let{
                            it.supportFragmentManager.commit {
                                addToBackStack(null)
                                add<FragmentMoviesDetails>(R.id.main_container)
                            }
                        }
        }

        return view
    }


}