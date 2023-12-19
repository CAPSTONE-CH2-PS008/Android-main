package com.ch2ps008.atomichabits.source

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TipsAndTrick(
    val id: Long,
    val photoUrl: String,
    val title: String,
    val description: String,
    val source: String
) : Parcelable