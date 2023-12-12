package com.ch2ps008.atomichabits.injection

import android.content.Context
import com.ch2ps008.atomichabits.repository.UserRepository
import com.ch2ps008.atomichabits.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }

}