package com.prongbang.mvi.user.presentation.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.prongbang.mvi.user.domain.User

class UserAdapter : ListAdapter<User, UserViewHolder>(DIFF_COMPARATOR) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
		return UserViewHolder.newInstance(parent)
	}

	override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
		holder.bind(getItem(position))
	}

	companion object {
		private val DIFF_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
			override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
				return oldItem == newItem
			}

			override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
				return oldItem.id == newItem.id
			}
		}
	}
}