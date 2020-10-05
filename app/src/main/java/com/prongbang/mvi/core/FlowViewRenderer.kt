package com.prongbang.mvi.core

interface FlowViewRenderer<State, Effect> {
	fun render(state: State)
	fun renderEffect(effects: Effect)
}