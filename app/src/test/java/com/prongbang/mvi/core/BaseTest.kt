package com.prongbang.mvi.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

open class BaseTest {

	@get:Rule
	val rule: TestRule = InstantTaskExecutorRule()

	@Before
	open fun setUp() {
		MockKAnnotations.init(this, relaxUnitFun = true)
	}
}