package com.prongbang.mvi.core

import com.prongbang.mvi.core.BaseTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before

open class ViewModelTest : BaseTest() {

	@ExperimentalCoroutinesApi
	@Before
	override fun setUp() {
		super.setUp()
		Dispatchers.setMain(TestCoroutineDispatcher())
	}
}