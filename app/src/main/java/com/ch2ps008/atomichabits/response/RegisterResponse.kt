package com.ch2ps008.atomichabits.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("body")
	val body: Body
)