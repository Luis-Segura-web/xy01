package com.xy01.iptvplayer.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xy01.iptvplayer.data.model.Profile
import com.xy01.iptvplayer.databinding.ItemProfileBinding

class ProfileAdapter(
    private val onProfileClick: (Profile) -> Unit,
    private val onDeleteClick: (Profile) -> Unit
) : ListAdapter<Profile, ProfileAdapter.ProfileViewHolder>(ProfileDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ItemProfileBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProfileViewHolder(binding, onProfileClick, onDeleteClick)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProfileViewHolder(
        private val binding: ItemProfileBinding,
        private val onProfileClick: (Profile) -> Unit,
        private val onDeleteClick: (Profile) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(profile: Profile) {
            binding.textProfileName.text = profile.name
            binding.textServerUrl.text = profile.serverUrl
            binding.textUsername.text = profile.username
            
            // Show active status
            binding.iconActive.visibility = if (profile.isActive) 
                android.view.View.VISIBLE else android.view.View.GONE
            
            binding.root.setOnClickListener {
                onProfileClick(profile)
            }
            
            binding.btnDelete.setOnClickListener {
                onDeleteClick(profile)
            }
        }
    }

    private class ProfileDiffCallback : DiffUtil.ItemCallback<Profile>() {
        override fun areItemsTheSame(oldItem: Profile, newItem: Profile): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Profile, newItem: Profile): Boolean {
            return oldItem == newItem
        }
    }
}