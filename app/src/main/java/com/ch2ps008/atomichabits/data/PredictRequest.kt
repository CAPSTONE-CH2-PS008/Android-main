package com.ch2ps008.atomichabits.data

data class PredictRequest (
    val Activity_Name: String,
    val Bobot: Int,
    val Activity: Int,
    val Start_Time: Int,
    val End_Time: Int,
    val Interest: Int,
    val Creation_Date: Long
)