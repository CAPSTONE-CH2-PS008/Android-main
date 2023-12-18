package com.ch2ps008.atomichabits.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ch2ps008.atomichabits.db.Habit
import com.ch2ps008.atomichabits.repository.UserRepository

class ListViewModel(private val repository: UserRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun getHabit() = repository.getHabitId()
}