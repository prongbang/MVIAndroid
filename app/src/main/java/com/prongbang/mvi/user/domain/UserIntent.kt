package com.prongbang.mvi.user.domain

sealed class UserIntent {
	object FetchUser : UserIntent()
}