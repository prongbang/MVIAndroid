package com.prongbang.mvi.user.data.remote

import com.prongbang.mvi.user.domain.User
import retrofit2.http.GET

interface ApiService {
	@GET("users")
	suspend fun getUsers(): List<User>
}