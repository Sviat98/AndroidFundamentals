package com.bashkevich.androidfundamentals.movieslist.view

import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashkevich.androidfundamentals.*
import com.bashkevich.androidfundamentals.databinding.FragmentMoviesListBinding
import com.bashkevich.androidfundamentals.model.viewobject.Movie
import com.bashkevich.androidfundamentals.moviesdetails.view.FragmentMoviesDetails
import com.bashkevich.androidfundamentals.movieslist.viewmodel.MoviesListViewModel
import com.bashkevich.androidfundamentals.movieslist.viewmodel.MoviesListViewModelFactory
import com.google.android.material.transition.MaterialElevationScale


class FragmentMoviesList : Fragment() {
    private var savedRecyclerLayoutState: Parcelable? = null

    private var _binding: FragmentMoviesListBinding? = null

    private val binding get() = _binding!!

    private val moviesListViewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(
            requireContext().applicationContext
        )
    }

    private val onMovieClickListener = object : OnMovieClickListener {
        override fun onMovieClick(movieId: Int, view: View) {
            exitTransition = MaterialElevationScale(false).apply {
                duration =
                    resources.getInteger(R.integer.shared_element_transition_duration).toLong()
            }

            reenterTransition = MaterialElevationScale(true).apply {
                duration =
                    resources.getInteger(R.integer.shared_element_transition_duration).toLong()
            }

            activity?.supportFragmentManager?.commit {
                addSharedElement(view, getString(R.string.movie_transition))
                addToBackStack(null)
                replace(R.id.main_container, FragmentMoviesDetails.newInstance(movieId))
            }
        }
    }

    private val moviesListAdapter = MoviesListAdapter(onMovieClickListener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

        setUpMoviesListRecyclerView()
        moviesListViewModel.loadMoviesList()

        moviesListViewModel.moviesListLiveData?.observe(this.viewLifecycleOwner) { movies ->
            movies?.let { setUpMoviesList(it) }

            (view.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }

        moviesListViewModel.errorLiveData.observe(this.viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpMoviesListRecyclerView() {
        val spanCount =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                3
            } else {
                2
            }
        binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, spanCount)
        binding.moviesRecyclerView.adapter = moviesListAdapter
        binding.moviesRecyclerView.addItemDecoration(MoviesDecoration())
    }

    private fun setUpMoviesList(movies: List<Movie>) {

        moviesListAdapter.bindMovies(movies)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (isStateSaved) {
            outState.putParcelable(
                BUNDLE_RECYCLER_LAYOUT,
                binding.moviesRecyclerView.layoutManager?.onSaveInstanceState()
            )
        } else {
            arguments = outState
            arguments?.putParcelable(
                BUNDLE_RECYCLER_LAYOUT,
                binding.moviesRecyclerView.layoutManager?.onSaveInstanceState()
            )
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT)
        } else {
            arguments?.let {
                savedRecyclerLayoutState = it.getParcelable(BUNDLE_RECYCLER_LAYOUT)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val BUNDLE_RECYCLER_LAYOUT = "BUNDLE_RECYCLER_LAYOUT"
    }
}