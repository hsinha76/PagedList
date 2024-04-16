package com.hsdroid.pagedlist.ui.view.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hsdroid.pagedlist.data.models.ServerResponse
import com.hsdroid.pagedlist.databinding.ItemDetailsBinding
import com.hsdroid.pagedlist.ui.view.activity.DetailsActivity
import com.hsdroid.pagedlist.ui.view.activity.HomeActivity
import javax.inject.Inject

class HomeAdapter @Inject constructor(val homeActivity: HomeActivity) :
    PagingDataAdapter<ServerResponse, HomeAdapter.MyHomeViewHolder>(Diff) {

    inner class MyHomeViewHolder(private val binding: ItemDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(serverResponse: ServerResponse) {

            with(binding) {
                tvId.text = buildString {
                    append("#")
                    append(serverResponse.id)
                }

                tvTitle.text = serverResponse.title

                itemView.setOnClickListener {
                    val intent = Intent(homeActivity, DetailsActivity::class.java)
                    intent.putExtra("details", serverResponse)
                    homeActivity.startActivity(intent)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyHomeViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHomeViewHolder {
        return MyHomeViewHolder(
            ItemDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    object Diff : DiffUtil.ItemCallback<ServerResponse>() {
        override fun areItemsTheSame(oldItem: ServerResponse, newItem: ServerResponse): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ServerResponse, newItem: ServerResponse): Boolean =
            oldItem == newItem
    }
}