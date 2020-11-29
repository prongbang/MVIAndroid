package com.prongbang.mvi.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

abstract class FlowViewModel<Intent, State, Effect> : ViewModel() {
	protected val state = MutableStateFlow(this.initState())
	protected val effect = MutableStateFlow(this.initEffect())
	protected val intents = Channel<Intent>(Channel.UNLIMITED)
	protected val states: Flow<State> get() = state
	protected val effects: Flow<Effect> get() = effect

	init {
		handleIntent()
	}

	private fun handleIntent() {
		viewModelScope.launch {
			intents.consumeAsFlow()
					.collect {
						onProcessIntent(it)
					}
		}
	}

	fun process(intent: Intent) {
		viewModelScope.launch(Dispatchers.Default) {
			intents.send(intent)
		}
	}

	fun stateSubscribe(onState: (State) -> Unit) {
		viewModelScope.launch {
			states.collect {
				onState.invoke(it)
			}
		}
	}

	fun effectSubscribe(onEffect: (Effect) -> Unit) {
		viewModelScope.launch {
			effects.collect {
				onEffect.invoke(it)
			}
		}
	}

	abstract fun initState(): State
	abstract fun initEffect(): Effect
	protected abstract fun onProcessIntent(intent: Intent)
}