package com.prongbang.mvi.user.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prongbang.mvi.databinding.ItemUserBinding
import com.prongbang.mvi.user.domain.User

class UserViewHolder(
		private val binding: ItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

	fun bind(item: User) {
		binding.textViewUserName.text = item.name
		binding.textViewUserEmail.text = item.email
		Glide.with(binding.imageViewAvatar.context)
				.load(item.avatar)
				.into(binding.imageViewAvatar)
	}

	companion object {
		fun newInstance(parent: ViewGroup): UserViewHolder {
			val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent,
					false)
			return UserViewHolder(binding)
		}
	}
}