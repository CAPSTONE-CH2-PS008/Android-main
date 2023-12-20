package com.ch2ps008.atomichabits.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.paging.DataSource
import androidx.room.Delete
import androidx.room.Query

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getHabit(): LiveData<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Query("DELETE FROM habits")
    suspend fun deleteDB()

    @Delete
    suspend fun deleteHabit(habit: Habit)
}