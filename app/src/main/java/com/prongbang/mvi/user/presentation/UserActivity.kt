package com.prongbang.mvi.user.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.prongbang.mvi.databinding.ActivityUserBinding
import com.prongbang.mvi.user.presentation.list.UserAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserActivity : AppCompatActivity() {

	private val viewModel: UserViewModel by viewModel()
	private val userAdapter by lazy { UserAdapter() }
	private val binding by lazy { ActivityUserBinding.inflate(layoutInflater) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		setupUI()
		observeViewModel()
		setupClicks()
	}

	private fun setupClicks() {
		binding.buttonFetchUser.setOnClickListener {
			lifecycleScope.launch {
				viewModel.intents.send(UserIntent.FetchUser)
			}
		}
	}

	private fun setupUI() {
		binding.apply {
			recyclerView.apply {
				layoutManager = LinearLayoutManager(this@UserActivity)
				addItemDecoration(DividerItemDecoration(context,
						(layoutManager as LinearLayoutManager).orientation))
				adapter = userAdapter
			}
		}
	}

	private fun observeViewModel() {
		viewModel.state.observe(this@UserActivity, Observer {
			render(it)
		})
	}

	private fun render(state: UserState) {
		when (state) {
			is UserState.Idle -> {
			}
			is UserState.Loading -> {
				binding.buttonFetchUser.visibility = View.GONE
				binding.progressBar.visibility = View.VISIBLE
			}
			is UserState.Users -> {
				binding.progressBar.visibility = View.GONE
				binding.buttonFetchUser.visibility = View.GONE
				binding.recyclerView.visibility = View.VISIBLE
				userAdapter.submitList(state.user)
			}
			is UserState.Error -> {
				binding.progressBar.visibility = View.GONE
				binding.buttonFetchUser.visibility = View.VISIBLE
				Toast.makeText(this@UserActivity, state.error, Toast.LENGTH_LONG)
						.show()
			}
		}
	}

	companion object {
		fun navigate(context: Context) {
			context.startActivity(Intent(context, UserActivity::class.java))
		}
	}
}