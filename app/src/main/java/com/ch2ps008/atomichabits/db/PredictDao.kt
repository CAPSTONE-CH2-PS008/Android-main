package com.ch2ps008.atomichabits.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PredictDao {
    @Query("SELECT * FROM predict")
    fun getPredict(): LiveData<List<Predict>>

    @Query("SELECT * FROM predict WHERE id = :id")
    suspend fun getPredictsById(id: Int): List<Predict>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPredict(predict: Predict)

    @Query("DELETE FROM predict")
    suspend fun deleteDB()

    @Delete
    suspend fun deletePredict(predict: Predict)
}