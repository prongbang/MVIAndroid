package com.prongbang.mvi.user.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prongbang.mvi.user.data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class UserViewModel(
		private val userRepository: UserRepository
) : ViewModel() {

	private val _state = MutableLiveData<UserState>()
	val intents = Channel<UserIntent>(Channel.UNLIMITED)
	val state: LiveData<UserState> get() = _state

	init {
		handleIntent()
	}

	private fun handleIntent() {
		viewModelScope.launch {
			for (state in intents) {
				when (state) {
					is UserIntent.FetchUser -> fetchUser()
				}
			}
		}
	}

	private fun fetchUser() {
		viewModelScope.launch(Dispatchers.IO) {
			_state.postValue(UserState.Loading)
			_state.postValue(try {
				UserState.Users(userRepository.getUsers())
			} catch (e: Exception) {
				UserState.Error(e.localizedMessage)
			})
		}
	}
}