package com.ch2ps008.atomichabits.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "activityName")
    val activityName: String,
    @ColumnInfo(name = "activityCategory")
    val activityCategory: Int,
    @ColumnInfo(name = "startHour")
    val startHour: Int,
    @ColumnInfo(name = "endHour")
    val endHour: Int,
    @ColumnInfo(name = "interest")
    val interest: Int,
)