package com.dasaevcompany.upax.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dasaevcompany.upax.databinding.FragmentMovieBinding
import com.dasaevcompany.upax.model.Movie
import com.dasaevcompany.upax.view.adapters.MovieAdapter
import com.dasaevcompany.upax.view.adapters.MovieListener
import com.dasaevcompany.upax.viewmodel.MovieDBViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment(), MovieListener {

    private val database : MovieDBViewModel by viewModels()
    private lateinit var binding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        observers()
    }

    private fun initData() {
        lifecycleScope.launch {
            database.getMovies()
        }
    }

    private fun observers(){
        database.listMovie.observe(requireActivity()) {
            if (it.isNotEmpty()) {
                val adapter = MovieAdapter(it, this)
                binding.recycler.adapter = adapter
            } else {
                lifecycleScope.launch {
                    database.getMovies()
                }
            }
        }

    }

    override fun clickOnHero(movie: Movie) {
        val dialogFragment = InfoMovieFragment(movie)
        dialogFragment.show(childFragmentManager,
            InfoMovieFragment::class.java.simpleName)
    }

}