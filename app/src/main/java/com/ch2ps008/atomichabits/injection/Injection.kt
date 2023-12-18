package com.ch2ps008.atomichabits.injection

import android.content.Context
import android.util.Log
import com.ch2ps008.atomichabits.R
import com.ch2ps008.atomichabits.repository.UserRepository
import com.ch2ps008.atomichabits.retrofit.ApiConfig
import com.ch2ps008.atomichabits.auth.UserPreference
import com.ch2ps008.atomichabits.auth.dataStore
import com.ch2ps008.atomichabits.db.HabitDao
import com.ch2ps008.atomichabits.db.HabitDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUser().first() }
        Log.d(context.getString(R.string.token_saved), user.uid)
        val apiService = ApiConfig.getApiService()
        val habitDatabase = HabitDatabase.getDatabase(context)
        val habitDao = habitDatabase.habitDao()

        return UserRepository.getInstance(apiService, pref, habitDao)
    }
}