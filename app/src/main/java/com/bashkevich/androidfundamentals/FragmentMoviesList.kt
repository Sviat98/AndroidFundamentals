package com.bashkevich.androidfundamentals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class FragmentMoviesList : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view  = inflater.inflate(R.layout.fragment_movies_list, container, false)

        val posterView =  view.findViewById<ImageView>(R.id.movie_poster)

        posterView.setOnClickListener{
                        this.activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                    ?.add(R.id.main_container,FragmentMoviesDetails())
                    ?.commit()
        }

        return view
    }


}