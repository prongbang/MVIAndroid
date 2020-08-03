package com.prongbang.mvi

import android.app.Application
import com.prongbang.mvi.di.contributorModule
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@InternalCoroutinesApi
class MainApplication : Application() {

	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidContext(this@MainApplication)
			modules(contributorModule)
		}
	}
}