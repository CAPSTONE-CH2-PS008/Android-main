package com.ch2ps008.atomichabits.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps008.atomichabits.repository.UserRepository
import kotlinx.coroutines.launch

class AddViewModel(private val repository: UserRepository) : ViewModel() {

    fun addHabit(
        activityName: String,
        bobot: Int,
        activityCategory: Int,
        startHour: Int,
        endHour: Int,
        interest: Int,
        creationDate: Long
    ) = viewModelScope.launch {
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
        Activity_Name: String,
        Bobot: Int,
        Activity: Int,
        Start_Time: Int,
        End_Time: Int,
        Interest: Int,
        Creation_Date: Long
    ) = repository.predict(Activity_Name, Bobot, Activity, Start_Time, End_Time, Interest, Creation_Date )

    fun saveResult(
        Activity_Name: String,
        result: Int,
        Creation_Date: Long,
        Start_Time: Int,
        End_Time: Int,
    ) = viewModelScope.launch {
        repository.saveResult(Activity_Name, result, Creation_Date, Start_Time, End_Time)
    }
}