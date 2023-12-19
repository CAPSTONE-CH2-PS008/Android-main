package com.ch2ps008.atomichabits.ui.tipsandtrick

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ch2ps008.atomichabits.repository.UserRepository

class TipsAndTrickViewModel(private val repository: UserRepository) : ViewModel() {

    fun getTips() = repository.getTips().asLiveData()
}