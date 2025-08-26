package com.xy01.iptvplayer.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xy01.iptvplayer.data.model.Movie
import com.xy01.iptvplayer.databinding.ItemMovieBinding

class MovieAdapter(
    private val onMovieClick: (Movie) -> Unit
) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MovieViewHolder(binding, onMovieClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val onMovieClick: (Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.textMovieTitle.text = movie.name
            
            // Show rating if available
            if (!movie.rating.isNullOrEmpty()) {
                binding.textRating.text = movie.rating
                binding.textRating.visibility = android.view.View.VISIBLE
            } else {
                binding.textRating.visibility = android.view.View.GONE
            }
            
            // Load movie poster
            if (!movie.streamIcon.isNullOrEmpty()) {
                Glide.with(binding.imageMoviePoster.context)
                    .load(movie.streamIcon)
                    .into(binding.imageMoviePoster)
            }
            
            binding.root.setOnClickListener {
                onMovieClick(movie)
            }
        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.streamId == newItem.streamId
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}