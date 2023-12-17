package com.ch2ps008.atomichabits.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps008.atomichabits.repository.UserRepository
import com.ch2ps008.atomichabits.auth.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun loginUser(username: String, password: String)=
        userRepository.login(username , password)

    fun saveSession(user: UserModel)=
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
}