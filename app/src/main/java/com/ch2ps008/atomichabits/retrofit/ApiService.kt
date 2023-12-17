package com.ch2ps008.atomichabits.retrofit

import com.ch2ps008.atomichabits.data.LoginRequest
import com.ch2ps008.atomichabits.data.RegisterRequest
import com.ch2ps008.atomichabits.response.LoginResponse
import com.ch2ps008.atomichabits.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
    @POST("/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

}