package com.ch2ps008.atomichabits.ui.list

import androidx.lifecycle.ViewModel
import com.ch2ps008.atomichabits.repository.UserRepository

class ListViewModel(private val repository: UserRepository) : ViewModel() {

    fun getHabitDao() = repository.getHabitDao()

    fun getPredictDao() = repository.getPredictDao()

    fun getHabitSortTime() = repository.getHabitsSortedByTime()
    fun getPredictSortTime() = repository.getPredictSortedByTime()
}