package com.ch2ps008.atomichabits.ui.register

import androidx.lifecycle.ViewModel
import com.ch2ps008.atomichabits.repository.UserRepository

class RegisterViewModel (private val userRepository: UserRepository) : ViewModel() {

    fun registerUser(name: String, username: String, password: String)=
        userRepository.register(name, username , password)

}