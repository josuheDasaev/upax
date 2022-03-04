package com.dasaevcompany.upax.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dasaevcompany.upax.R
import com.dasaevcompany.upax.databinding.FragmentInfoMovieBinding
import com.dasaevcompany.upax.model.Movie
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InfoMovieFragment(movie : Movie) : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentInfoMovieBinding
    private val movies = movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            txName.text = movies.name
            txCal.text = String.format(getString(R.string.rate),movies.rate)
            txDescription.text = movies.description
            txDate.text = movies.date

            val photo = "https://image.tmdb.org/t/p/w500"+movies.picture

            Glide.with(requireActivity())
                .load(photo)
                .centerCrop()
                .into(binding.imMovie)
        }
    }

}