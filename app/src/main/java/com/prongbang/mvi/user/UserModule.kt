package com.prongbang.mvi.user

import com.prongbang.mvi.core.RetrofitBuilder
import com.prongbang.mvi.user.data.DefaultUserRepository
import com.prongbang.mvi.user.data.UserDataSource
import com.prongbang.mvi.user.data.UserRepository
import com.prongbang.mvi.user.data.remote.ApiService
import com.prongbang.mvi.user.data.remote.UserRemoteDataSource
import com.prongbang.mvi.user.presentation.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userModule = module {
	single { RetrofitBuilder.create(ApiService::class.java) }
	single<UserDataSource> { UserRemoteDataSource(get()) }
	single<UserRepository> { DefaultUserRepository(get()) }
	viewModel { UserViewModel(get()) }
}