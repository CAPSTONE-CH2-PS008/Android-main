package com.ch2ps008.atomichabits.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps008.atomichabits.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun loginUser(email: String, password: String)=
        userRepository.login(email, password)

}