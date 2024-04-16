package com.hsdroid.pagedlist.ui.view.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hsdroid.pagedlist.databinding.ActivityHomeBinding
import com.hsdroid.pagedlist.ui.view.adapter.HomeAdapter
import com.hsdroid.pagedlist.ui.view.adapter.HomeLoadingAdapter
import com.hsdroid.pagedlist.ui.viewModel.HomeViewModel
import com.hsdroid.pagedlist.utils.Helper.Companion.isInternetConnected
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()
    private val homeAdapter by lazy {
        HomeAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

        if (isInternetConnected(this)) {

            binding.tvNoInternet.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE

            lifecycleScope.launch {
                homeViewModel.getResponse().collectLatest {
                    homeAdapter.submitData(it)
                }
            }

            binding.recyclerView.apply {
                adapter = homeAdapter.withLoadStateHeaderAndFooter(
                    header = HomeLoadingAdapter { homeAdapter.retry() },
                    footer = HomeLoadingAdapter { homeAdapter.retry() }
                )
            }

        } else {
            binding.tvNoInternet.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }

    }
}