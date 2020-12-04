package com.bashkevich.androidfundamentals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashkevich.androidfundamentals.data.DataList


class FragmentMoviesDetails : Fragment() {
    private  var actorsRecyclerView : RecyclerView? =null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)

        actorsRecyclerView  =view.findViewById(R.id.actors_recycler_view)

        actorsRecyclerView?.adapter = ActorsAdapter().apply {
            bindActors(DataList().getActors())
        }
        actorsRecyclerView?.addItemDecoration(ActorsDecoration())
        actorsRecyclerView?.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL,false)


        return view
    }

    override fun onDetach() {
        super.onDetach()
        actorsRecyclerView = null
    }
}