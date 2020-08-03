package com.prongbang.mvi.user.presentation

import com.prongbang.mvi.user.domain.User

sealed class UserState {
	object Idle : UserState()
	object Loading : UserState()
	data class Users(val user: List<User>) : UserState()
	data class Error(val error: String?) : UserState()
}