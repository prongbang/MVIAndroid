package com.prongbang.mvi.user.data

import com.prongbang.mvi.user.domain.User

interface UserRepository {
	suspend fun getUsers(): List<User>
}

class DefaultUserRepository(
		private val userRemoteDataSource: UserDataSource
) : UserRepository {

	override suspend fun getUsers(): List<User> {
		return userRemoteDataSource.getUsers()
	}

}