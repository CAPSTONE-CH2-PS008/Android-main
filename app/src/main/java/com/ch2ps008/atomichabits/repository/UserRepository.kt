package com.ch2ps008.atomichabits.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.ch2ps008.atomichabits.BuildConfig.BASE_URL2
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
import com.ch2ps008.atomichabits.data.PredictRequest
import com.ch2ps008.atomichabits.db.Habit
import com.ch2ps008.atomichabits.db.HabitDao
import com.ch2ps008.atomichabits.db.Predict
import com.ch2ps008.atomichabits.db.PredictDao
import com.ch2ps008.atomichabits.response.PredictResponse
import com.ch2ps008.atomichabits.source.TipsAndTrick
import com.ch2ps008.atomichabits.source.TipsAndTrickData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar

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

    fun getNearestHabit(): LiveData<Habit?> {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return habitDao.getHabitsSortedByTime().map { habits ->
            habits.minByOrNull { habit ->
                val diffStart = if (habit.startHour >= currentHour) habit.startHour - currentHour else 24 + habit.startHour - currentHour
                val diffEnd = if (habit.endHour >= currentHour) habit.endHour - currentHour else 24 + habit.endHour - currentHour
                kotlin.math.min(diffStart, diffEnd)
            }
        }
    }

    fun predict(
        Activity_Name: String,
        Bobot: Int,
        Activity: Int,
        Start_Time: Int,
        End_Time: Int,
        Interest: Int,
        Creation_Date: Long
    ): LiveData<Result<PredictResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val loginResponse = apiService.predict(BASE_URL2,
                    PredictRequest(Activity_Name, Bobot, Activity, Start_Time, End_Time, Interest, Creation_Date)
                )
                emit(Result.Success(loginResponse))

            } catch (e: HttpException) {
                val error = e.response()?.errorBody()?.string()
                val errorRes = Gson().fromJson(error, ErrorResponse::class.java)
                Log.d(TAG, "predict: ${e.message.toString()}")
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

    suspend fun insertHabit(
        activityName: String,
        bobot: Int,
        spinnerActivity: Int,
        startHour: Int,
        endHour: Int,
        spinnerInterest: Int,
        creationDate: Long
    ) {
        val newHabit = Habit(
            activityName = activityName,
            bobot = bobot,
            activityCategory = spinnerActivity,
            startHour = startHour,
            endHour = endHour,
            interest = spinnerInterest,
            creationDate = creationDate,
        )
        habitDao.insertHabit(newHabit)
    }

    suspend fun saveResult(activityName: String, result: Int, creationDate: Long, Start_Time: Int, End_Time: Int){
        val lastInsertedHabitId = habitDao.getLastInsertedHabitId()

        val predictResult = Predict(
            activityName = activityName,
            result = result,
            creationDate = creationDate,
            startHour = Start_Time,
            endHour = End_Time,
            id = lastInsertedHabitId,
        )
        predictDao.insertPredict(predictResult)
    }

    suspend fun deleteDBHabit() {
        habitDao.deleteDB()
    }

    suspend fun deleteDBPredict() {
        predictDao.deleteDB()
    }


    fun getHabitDao(): HabitDao {
        return habitDao
    }

    fun getPredictDao(): PredictDao {
        return predictDao
    }

    fun getHabitsSortedByTime(): LiveData<List<Habit>> {
        return habitDao.getHabitsSortedByTime()
    }

    fun getPredictSortedByTime(): LiveData<List<Predict>> {
        return predictDao.getPredictSortedByTime()
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