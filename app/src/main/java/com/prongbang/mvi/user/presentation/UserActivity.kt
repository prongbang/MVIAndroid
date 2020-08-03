package com.prongbang.mvi.user.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.prongbang.mvi.databinding.ActivityUserBinding
import com.prongbang.mvi.user.domain.User
import com.prongbang.mvi.user.presentation.list.UserAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
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
				viewModel.userIntent.send(UserIntent.FetchUser)
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
		lifecycleScope.launch {
			viewModel.state.collect(collector = object : FlowCollector<UserState> {
				override suspend fun emit(value: UserState) {
					when (value) {
						is UserState.Idle -> {
						}
						is UserState.Loading -> {
							binding.buttonFetchUser.visibility = View.GONE
							binding.progressBar.visibility = View.VISIBLE
						}
						is UserState.Users -> {
							binding.progressBar.visibility = View.GONE
							binding.buttonFetchUser.visibility = View.GONE
							renderList(value.user)
						}
						is UserState.Error -> {
							binding.progressBar.visibility = View.GONE
							binding.buttonFetchUser.visibility = View.VISIBLE
							Toast.makeText(this@UserActivity, value.error, Toast.LENGTH_LONG)
									.show()
						}
					}
				}
			})
		}
	}

	private fun renderList(users: List<User>) {
		binding.recyclerView.visibility = View.VISIBLE
		userAdapter.submitList(users)
	}

	companion object {
		fun navigate(context: Context) {
			context.startActivity(Intent(context, UserActivity::class.java))
		}
	}
}