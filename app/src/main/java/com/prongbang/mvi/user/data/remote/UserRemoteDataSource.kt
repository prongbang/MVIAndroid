package com.prongbang.mvi.user.data.remote

import com.prongbang.mvi.user.data.UserDataSource
import com.prongbang.mvi.user.domain.User

class UserRemoteDataSource(
		private val apiService: ApiService
) : UserDataSource {

	override suspend fun getUsers(): List<User> = apiService.getUsers()
}