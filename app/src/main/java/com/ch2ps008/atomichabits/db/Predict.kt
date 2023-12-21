package com.ch2ps008.atomichabits.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "predict")
@Parcelize
data class Predict (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "activityName")
    val activityName: String,
    @ColumnInfo(name = "result")
    val result: Int,
    @ColumnInfo(name = "creationDate")
    val creationDate: Long,
    @ColumnInfo(name = "startHour")
    val startHour: Int,
    @ColumnInfo(name = "endHour")
    val endHour: Int,
): Parcelable