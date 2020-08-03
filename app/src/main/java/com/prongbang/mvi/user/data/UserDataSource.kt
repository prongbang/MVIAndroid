package com.prongbang.mvi.user.data

import com.prongbang.mvi.user.domain.User

interface UserDataSource {
	suspend fun getUsers(): List<User>
}