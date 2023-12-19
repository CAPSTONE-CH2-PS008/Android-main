package com.ch2ps008.atomichabits.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.ch2ps008.atomichabits.db.Habit
import com.ch2ps008.atomichabits.repository.UserRepository
import kotlinx.coroutines.launch

class ListViewModel(private val repository: UserRepository) : ViewModel() {

    fun getHabit() = repository.getHabitId()

    fun deleteTask(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }
}