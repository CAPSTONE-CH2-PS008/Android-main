package com.ch2ps008.atomichabits.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("body")
	val body: Body
)

data class Body(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("emailVerified")
	val emailVerified: Boolean,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("isAnonymous")
	val isAnonymous: Boolean,

	@field:SerializedName("lastLoginAt")
	val lastLoginAt: String,

	@field:SerializedName("apiKey")
	val apiKey: String,

	@field:SerializedName("displayName")
	val displayName: String,

	@field:SerializedName("appName")
	val appName: String,

	@field:SerializedName("email")
	val email: String
)