package com.test.pwassignment.domain.model


import com.google.gson.annotations.SerializedName

data class QuizStreak(
    @SerializedName("day")
    val day: String,
    @SerializedName("status")
    val status: String
)