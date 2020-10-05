package com.prongbang.mvi.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
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
					.collect(object : FlowCollector<Intent> {
						override suspend fun emit(value: Intent) {
							onProcessIntent(value)
						}
					})
		}
	}

	fun process(intent: Intent) {
		viewModelScope.launch(Dispatchers.Default) {
			intents.send(intent)
		}
	}

	fun stateSubscribe(onState: (State) -> Unit) {
		viewModelScope.launch {
			states.collect(object : FlowCollector<State> {
				override suspend fun emit(value: State) {
					onState.invoke(value)
				}
			})
		}
	}

	fun effectSubscribe(onEffect: (Effect) -> Unit) {
		viewModelScope.launch {
			effects.collect(object : FlowCollector<Effect> {
				override suspend fun emit(value: Effect) {
					onEffect.invoke(value)
				}
			})
		}
	}

	abstract fun initState(): State
	abstract fun initEffect(): Effect
	protected abstract fun onProcessIntent(intent: Intent)
}