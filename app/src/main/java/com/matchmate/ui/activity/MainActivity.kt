package com.matchmate.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.matchmate.R
import com.matchmate.data.model.User
import com.matchmate.data.network.Resource
import com.matchmate.databinding.ActivityMainBinding
import com.matchmate.ui.adapter.MatchAdapter
import com.matchmate.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MatchAdapter.MatchClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MatchAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    fun init() {
        setUpRecyclerView()
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.users.collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.layoutContent.visibility = View.GONE
                        }

                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.layoutContent.visibility = View.VISIBLE
                            adapter.setUsers(resource.value)
                        }

                        is Resource.Failure -> {
                            Toast.makeText(this@MainActivity, resource.errorMsg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = MatchAdapter(this)
        binding.rvMatches.adapter = adapter
        binding.rvMatches.layoutManager = LinearLayoutManager(this)
    }

    override fun onAcceptClicked(user: User) {
        lifecycleScope.launch {
            viewModel.acceptUser(user.email)
        }
    }

    override fun onDeclineClicked(user: User) {
        lifecycleScope.launch {
            viewModel.declineUser(user.email)
        }
    }
}