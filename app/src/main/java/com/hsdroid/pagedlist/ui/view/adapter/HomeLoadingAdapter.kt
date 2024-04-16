package com.hsdroid.pagedlist.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hsdroid.pagedlist.databinding.ItemLoadingBinding

class HomeLoadingAdapter(private val retryCallback: () -> Unit) :
    LoadStateAdapter<HomeLoadingAdapter.MyHomeLoadingAdapter>() {

    inner class MyHomeLoadingAdapter(private val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener {
                retryCallback.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressCircular.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onBindViewHolder(holder: MyHomeLoadingAdapter, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MyHomeLoadingAdapter {
        return MyHomeLoadingAdapter(
            ItemLoadingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}