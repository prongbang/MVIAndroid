package com.prongbang.mvi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prongbang.mvi.databinding.ActivityMainBinding
import com.prongbang.mvi.user.presentation.UserActivity

class MainActivity : AppCompatActivity() {

	private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		binding.navigateUser.setOnClickListener {
			UserActivity.navigate(this)
		}
	}
}