package com.prongbang.mvi.user.presentation

sealed class UserIntent {
	object FetchUser : UserIntent()
}