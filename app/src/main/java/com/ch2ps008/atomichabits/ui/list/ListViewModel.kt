package com.ch2ps008.atomichabits.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps008.atomichabits.db.Habit
import com.ch2ps008.atomichabits.db.Predict
import com.ch2ps008.atomichabits.repository.UserRepository
import kotlinx.coroutines.launch

class ListViewModel(private val repository: UserRepository) : ViewModel() {

    fun getHabit() = repository.getHabitId()

    fun getPredict() = repository.getPredict()

    fun getHabitDao() = repository.getHabitDao()

    fun getPredictDao() = repository.getPredictDao()
}