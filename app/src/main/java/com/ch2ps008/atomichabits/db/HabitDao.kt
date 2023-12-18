package com.ch2ps008.atomichabits.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.paging.DataSource
import androidx.room.Query

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getHabit(): LiveData<List<Habit>>

    @Query("SELECT * FROM habits WHERE id = :habitId")
    fun getHabitById(habitId: Int): LiveData<Habit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Query("DELETE FROM habits")
    suspend fun deleteDB()
}