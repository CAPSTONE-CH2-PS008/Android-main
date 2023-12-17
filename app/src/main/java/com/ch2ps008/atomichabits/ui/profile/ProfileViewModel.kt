package com.ch2ps008.atomichabits.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ch2ps008.atomichabits.repository.UserRepository
import com.ch2ps008.atomichabits.auth.UserModel
import kotlinx.coroutines.launch

class ProfileViewModel (private val userRepository: UserRepository) : ViewModel(){

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}