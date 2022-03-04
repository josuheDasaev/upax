package com.dasaevcompany.upax.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dasaevcompany.upax.R
import com.dasaevcompany.upax.databinding.ViewMovieBinding
import com.dasaevcompany.upax.model.Movie

class MovieAdapter (
    private val list: List<Movie>,
    private val listener: MovieListener
) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_movie,parent,false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.createView(item)
        holder.click(item,listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val binding = ViewMovieBinding.bind(view)

        fun createView(movie : Movie) {
            val photo = "https://image.tmdb.org/t/p/w500"+movie.picture

            Glide.with(itemView.context)
                .load(photo)
                .centerCrop()
                .into(binding.imMovie)
            binding.txName.text = movie.name
        }

        fun click(movie: Movie,listener: MovieListener){
            binding.item.setOnClickListener {
                listener.clickOnHero(movie)
            }
        }

    }
}