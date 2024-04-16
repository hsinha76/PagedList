package com.hsdroid.pagedlist.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServerResponse(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
) : Parcelable