package com.ch2ps008.atomichabits.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps008.atomichabits.db.Habit
import com.ch2ps008.atomichabits.repository.UserRepository
import kotlinx.coroutines.launch

class AddViewModel(private val repository: UserRepository): ViewModel() {

    fun addHabit(
        activityName: String,
        activityCategory: Int,
        startHour: Int,
        endHour: Int,
        interest: Int
    ) = viewModelScope.launch {
        repository.insertHabit(activityName, activityCategory, startHour, endHour, interest)
    }

}