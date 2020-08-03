package com.prongbang.mvi.di

import com.prongbang.mvi.user.userModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
val contributorModule = listOf(
		userModule
)