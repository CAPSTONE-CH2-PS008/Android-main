package com.ch2ps008.atomichabits.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("end time")
	val endTime: Int,

	@field:SerializedName("result")
	val result: Int,

	@field:SerializedName("start time")
	val startTime: Int
)
