package com.hsdroid.pagedlist.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hsdroid.pagedlist.data.models.ServerResponse
import com.hsdroid.pagedlist.data.network.APIService
import com.hsdroid.pagedlist.data.repository.ListPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: APIService) : ViewModel() {
    fun getResponse(): Flow<PagingData<ServerResponse>> = Pager(
        config = PagingConfig(
            8,
            enablePlaceholders = false,
            prefetchDistance = 2,
            initialLoadSize = 8
        )
    ) {
        ListPagingSource(apiService)
    }.flow.cachedIn(viewModelScope)

}