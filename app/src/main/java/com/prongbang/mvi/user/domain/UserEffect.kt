package com.prongbang.mvi.user.domain

sealed class UserEffect {
	object Idle : UserEffect()
	data class Error(val error: String?) : UserEffect()
}