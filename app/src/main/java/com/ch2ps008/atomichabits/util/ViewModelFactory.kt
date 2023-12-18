package com.ch2ps008.atomichabits.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2ps008.atomichabits.injection.Injection
import com.ch2ps008.atomichabits.repository.UserRepository
import com.ch2ps008.atomichabits.ui.add.AddViewModel
import com.ch2ps008.atomichabits.ui.login.LoginViewModel
import com.ch2ps008.atomichabits.ui.main.MainViewModel
import com.ch2ps008.atomichabits.ui.profile.ProfileViewModel
import com.ch2ps008.atomichabits.ui.register.RegisterViewModel
import com.ch2ps008.atomichabits.ui.welcome.WelcomeViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userRepository) as T
            }
        else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userRepository) as T
        }
        else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository) as T
        }
        else if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel(userRepository) as T
        }
        else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userRepository) as T
        }
        else if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            return AddViewModel(userRepository) as T
        }
        throw IllegalArgumentException("No ModelClass: " + modelClass.name)
    }

    companion object {
        fun getInstance(context: Context): ViewModelFactory {
            return ViewModelFactory(Injection.provideRepository(context))
        }
    }
}