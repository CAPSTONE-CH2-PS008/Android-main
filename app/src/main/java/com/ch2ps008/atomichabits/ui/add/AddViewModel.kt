package com.ch2ps008.atomichabits.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps008.atomichabits.db.Habit
import com.ch2ps008.atomichabits.repository.UserRepository
import kotlinx.coroutines.launch

class AddViewModel(private val repository: UserRepository) : ViewModel() {

    fun addHabit(
        activityName: String,
        bobot: Int,
        activityCategory: Int,
        startHour: Int,
        endHour: Int,
        interest: Int
    ) = viewModelScope.launch {
        val creationDate = System.currentTimeMillis()
        repository.insertHabit(
            activityName,
            bobot,
            activityCategory,
            startHour,
            endHour,
            interest,
            creationDate
        )
    }

    fun postPredict(
        Bobot: Int,
        Activity: Int,
        Start_Time: Int,
        End_Time: Int,
        Interest: Int
    ) = repository.predict(Bobot, Activity, Start_Time, End_Time, Interest)

    fun saveResult(
        result: Int
    ) = viewModelScope.launch {
        repository.saveResult(result)
    }
}