package com.prongbang.mvi.user.presentation

import androidx.lifecycle.Observer
import com.prongbang.mvi.core.ViewModelTest
import com.prongbang.mvi.user.data.UserRepository
import com.prongbang.mvi.user.domain.User
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UserViewModelTest : ViewModelTest() {

	@MockK
	lateinit var userRepository: UserRepository

	@MockK
	lateinit var observer: Observer<UserState>

	private val viewModel by lazy { UserViewModel(userRepository) }

	@Test
	fun fetchUser_ShouldHasUserList_WhenFetchSuccess() = runBlocking {
		// Given
		val userList = listOf(User())
		coEvery { userRepository.getUsers() } returns userList

		// When
		viewModel.intents.send(UserIntent.FetchUser)

		// Then
		viewModel.state.observeForever(observer)
		verifySequence {
			observer.onChanged(UserState.Loading)
			observer.onChanged(UserState.Users(userList))
		}
	}

}