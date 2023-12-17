package com.ch2ps008.atomichabits.auth

data class UserModel (
    val uid: String,
    val isLogin: Boolean = false
)