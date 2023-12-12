package com.ch2ps008.atomichabits.injection

import android.content.Context
import android.util.Log
import com.ch2ps008.atomichabits.repository.UserRepository
import com.ch2ps008.atomichabits.retrofit.ApiConfig
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }

}