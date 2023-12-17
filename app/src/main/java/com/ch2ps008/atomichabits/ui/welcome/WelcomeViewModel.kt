package com.ch2ps008.atomichabits.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ch2ps008.atomichabits.repository.UserRepository
import com.ch2ps008.atomichabits.auth.UserModel

class WelcomeViewModel (private val userRepository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

}