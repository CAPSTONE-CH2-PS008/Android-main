package com.ch2ps008.atomichabits.ui.login

import androidx.lifecycle.ViewModel
import com.ch2ps008.atomichabits.repository.UserRepository
import org.json.JSONObject

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun loginUser(username: String, password: String)=
        userRepository.login(username , password)

}