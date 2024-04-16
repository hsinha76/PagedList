package com.hsdroid.pagedlist.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hsdroid.pagedlist.data.models.ServerResponse
import com.hsdroid.pagedlist.data.network.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ListPagingSource @Inject constructor(private val apiService: APIService) :
    PagingSource<Int, ServerResponse>() {
    override fun getRefreshKey(state: PagingState<Int, ServerResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ServerResponse> {
        val pageIndex = params.key ?: 0

        return try {
            val data = apiService.getResponse()
            val pageSize = params.loadSize
            val start = pageIndex * pageSize
            val end = minOf(start + pageSize, data.size)

            LoadResult.Page(
                data = data.subList(start, end),
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (end == data.size) null else pageIndex + 1
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}