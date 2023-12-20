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
import com.ch2ps008.atomichabits.response.RegisterResponse
import com.ch2ps008.atomichabits.auth.UserModel
import com.ch2ps008.atomichabits.auth.UserPreference
import com.ch2ps008.atomichabits.db.Habit
import com.ch2ps008.atomichabits.db.HabitDao
import com.ch2ps008.atomichabits.db.Predict
import com.ch2ps008.atomichabits.db.PredictDao
import com.ch2ps008.atomichabits.source.TipsAndTrick
import com.ch2ps008.atomichabits.source.TipsAndTrickData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
    private val habitDao: HabitDao,
    private val predictDao: PredictDao
) {

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

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> =
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

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun getTips(): Flow<List<TipsAndTrick>> = flow {
        emit(TipsAndTrickData.tips)
    }

    fun getHabitId(): LiveData<List<Habit>> = habitDao.getHabit()

    fun getPredict(): LiveData<List<Predict>> = predictDao.getPredict()

    suspend fun insertHabit(
        activityName: String,
        spinnerActivity: Int,
        startHour: Int,
        endHour: Int,
        spinnerInterest: Int,
        creationDate: Long
    ) {
        val newHabit = Habit(
            activityName = activityName,
            activityCategory = spinnerActivity,
            startHour = startHour,
            endHour = endHour,
            interest = spinnerInterest,
            creationDate = creationDate
        )
        habitDao.insertHabit(newHabit)
    }

    suspend fun undoHabit(habit: Habit) {
        habitDao.insertHabit(habit)
    }

    suspend fun deleteDB() {
        habitDao.deleteDB()
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit)
    }

    companion object {
        private const val TAG = "UserRepository"

        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference,
            habitDao: HabitDao,
            predictDao: PredictDao
        ): UserRepository = UserRepository(userPreference, apiService, habitDao, predictDao )
    }
}