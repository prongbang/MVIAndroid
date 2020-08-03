package com.prongbang.mvi.user.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prongbang.mvi.user.data.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class UserViewModel(
		private val userRepository: UserRepository
) : ViewModel() {

	private val _state = MutableStateFlow<UserState>(UserState.Idle)
	val userIntent = Channel<UserIntent>(Channel.UNLIMITED)
	val state: StateFlow<UserState> get() = _state

	init {
		handleIntent()
	}

	private fun handleIntent() {
		viewModelScope.launch {
//			userIntent.consumeEach {
//				when (it) {
//					is UserIntent.FetchUser -> fetchUser()
//				}
//			}
			userIntent.consumeAsFlow()
					.collect(object : FlowCollector<UserIntent> {
						override suspend fun emit(value: UserIntent) {
							when (value) {
								is UserIntent.FetchUser -> fetchUser()
							}
						}
					})
		}
	}

	private fun fetchUser() {
		viewModelScope.launch {
			_state.value = UserState.Loading
			_state.value = try {
				UserState.Users(userRepository.getUsers())
			} catch (e: Exception) {
				UserState.Error(e.localizedMessage)
			}
		}
	}
}