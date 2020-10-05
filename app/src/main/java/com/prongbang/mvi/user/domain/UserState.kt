package com.prongbang.mvi.user.domain

sealed class UserState {
	object Idle : UserState()
	object Loading : UserState()
	data class Users(val data: List<User>) : UserState()
}