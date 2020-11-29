package com.prongbang.mvi.user.presentation

import androidx.lifecycle.viewModelScope
import com.prongbang.mvi.core.FlowViewModel
import com.prongbang.mvi.user.data.UserRepository
import com.prongbang.mvi.user.domain.UserEffect
import com.prongbang.mvi.user.domain.UserIntent
import com.prongbang.mvi.user.domain.UserState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class UserViewModel(
		private val userRepository: UserRepository
) : FlowViewModel<UserIntent, UserState, UserEffect>() {

	override fun initState() = UserState.Idle
	override fun initEffect() = UserEffect.Idle

	override fun onProcessIntent(intent: UserIntent) {
		when (intent) {
			is UserIntent.FetchUser -> processFetchUser()
		}
	}

	private fun processFetchUser() {
		val handler = CoroutineExceptionHandler { _, exception ->
			effect.value = UserEffect.Error(exception.message)
		}
		viewModelScope.launch(handler) {
			state.value = UserState.Loading
			state.value = UserState.Users(userRepository.getUsers())
		}
	}
}