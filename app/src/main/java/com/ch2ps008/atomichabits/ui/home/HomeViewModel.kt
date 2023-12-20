package com.ch2ps008.atomichabits.ui.home

import androidx.lifecycle.ViewModel
import com.ch2ps008.atomichabits.repository.UserRepository

class HomeViewModel(private val repository: UserRepository) : ViewModel() {
    fun getHabit() = repository.getHabitId()

    fun getNearestHabit() = repository.getNearestHabit()
}