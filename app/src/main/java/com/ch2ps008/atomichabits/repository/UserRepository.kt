package com.ch2ps008.atomichabits.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ch2ps008.atomichabits.response.ErrorResponse
import com.ch2ps008.atomichabits.response.LoginResponse
import com.ch2ps008.atomichabits.retrofit.ApiService
import com.google.gson.Gson
import retrofit2.HttpException
import com.ch2ps008.atomichabits.response.Result
import com.ch2ps008.atomichabits.data.LoginRequest
import com.ch2ps008.atomichabits.data.RegisterRequest

class UserRepository private constructor(
    private val apiService: ApiService
){

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val loginResponse = apiService.login(LoginRequest(email, password))
                emit(Result.Success(loginResponse))

            } catch (e: HttpException) {
                val error = e.response()?.errorBody()?.string()
                val errorRes = Gson().fromJson(error, ErrorResponse::class.java)
                Log.d(TAG, "login: ${e.message.toString()}")
                emit(Result.Error(errorRes.error))
            } catch (e: Exception) {
                emit(Result.Error(e.toString()))
            }
        }

    fun register(name: String, email: String, password: String): LiveData<Result<LoginResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val loginResponse = apiService.register(RegisterRequest(name, email, password))
                emit(Result.Success(loginResponse))

            } catch (e: HttpException) {
                val error = e.response()?.errorBody()?.string()
                val errorRes = Gson().fromJson(error, ErrorResponse::class.java)
                Log.d(TAG, "register: ${e.message.toString()}")
                emit(Result.Error(errorRes.error))
            } catch (e: Exception) {
                emit(Result.Error(e.toString()))
            }
        }

    companion object {
        private const val TAG = "UserRepository"

        fun getInstance(
            apiService: ApiService
        ): UserRepository = UserRepository(apiService)
    }
}