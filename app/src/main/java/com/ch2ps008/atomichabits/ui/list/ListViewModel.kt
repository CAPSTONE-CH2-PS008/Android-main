package com.ch2ps008.atomichabits.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.ch2ps008.atomichabits.db.Habit
import com.ch2ps008.atomichabits.db.Predict
import com.ch2ps008.atomichabits.repository.UserRepository
import kotlinx.coroutines.launch

class ListViewModel(private val repository: UserRepository) : ViewModel() {

    fun getHabit() = repository.getHabitId()

    fun getPredict() = repository.getPredict()

    fun deleteTask(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }

    fun deletePredict(predict: Predict) {
        viewModelScope.launch {
            repository.deletePredict(predict)
        }
    }

    fun undoHabit(habit: Habit) {
        viewModelScope.launch {
            repository.undoHabit(habit)
        }
    }
    fun undoPredict(predict: Predict) {
        viewModelScope.launch {
            repository.undoPredict(predict)
        }
    }

    fun deletePredict(predict: Predict) {
        viewModelScope.launch {
            // Anda perlu menambahkan fungsi deletePredict di UserRepository
            repository.deletePredict(predict)
        }
    }

    fun undoPredict(predict: Predict) {
        viewModelScope.launch {
            // Anda perlu menambahkan fungsi undoPredict di UserRepository
            repository.undoPredict(predict)
        }
    }

}