package com.ch2ps008.atomichabits.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("error")
	val error: String
)
