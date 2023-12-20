package com.ch2ps008.atomichabits.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("result")
	val result: Int
)
