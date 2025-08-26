package com.xy01.iptvplayer.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xy01.iptvplayer.data.model.Series
import com.xy01.iptvplayer.databinding.ItemSeriesBinding

class SeriesAdapter(
    private val onSeriesClick: (Series) -> Unit
) : ListAdapter<Series, SeriesAdapter.SeriesViewHolder>(SeriesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val binding = ItemSeriesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SeriesViewHolder(binding, onSeriesClick)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SeriesViewHolder(
        private val binding: ItemSeriesBinding,
        private val onSeriesClick: (Series) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(series: Series) {
            binding.textSeriesTitle.text = series.name
            
            // Show genre if available
            if (!series.genre.isNullOrEmpty()) {
                binding.textGenre.text = series.genre
                binding.textGenre.visibility = android.view.View.VISIBLE
            } else {
                binding.textGenre.visibility = android.view.View.GONE
            }
            
            // Show rating if available
            if (!series.rating.isNullOrEmpty()) {
                binding.textRating.text = series.rating
                binding.textRating.visibility = android.view.View.VISIBLE
            } else {
                binding.textRating.visibility = android.view.View.GONE
            }
            
            // Load series cover
            if (!series.cover.isNullOrEmpty()) {
                Glide.with(binding.imageSeriesCover.context)
                    .load(series.cover)
                    .into(binding.imageSeriesCover)
            }
            
            binding.root.setOnClickListener {
                onSeriesClick(series)
            }
        }
    }

    private class SeriesDiffCallback : DiffUtil.ItemCallback<Series>() {
        override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem.seriesId == newItem.seriesId
        }

        override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem == newItem
        }
    }
}