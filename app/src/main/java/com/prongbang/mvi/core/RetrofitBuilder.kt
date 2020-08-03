package com.prongbang.mvi.core

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @ref https://blog.mindorks.com/mvi-architecture-android-tutorial-for-beginners-step-by-step-guide
 */
object RetrofitBuilder {

	private const val BASE_URL = "https://5e510330f2c0d300147c034c.mockapi.io/"

	private fun getRetrofit() = Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(MoshiConverterFactory.create())
			.build()

	fun <T> create(clazz: Class<T>): T = getRetrofit().create(clazz)
}