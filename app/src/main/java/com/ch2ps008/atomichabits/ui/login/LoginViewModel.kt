package com.ch2ps008.atomichabits.ui.login

import androidx.lifecycle.ViewModel
import com.ch2ps008.atomichabits.repository.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun loginUser(email: String, password: String)=
        userRepository.login(email, password)

}