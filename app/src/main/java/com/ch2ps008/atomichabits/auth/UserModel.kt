package com.ch2ps008.atomichabits.auth

data class UserModel (
    val uid: String,
    val username: String,
    val email: String,
    val isLogin: Boolean = false
)