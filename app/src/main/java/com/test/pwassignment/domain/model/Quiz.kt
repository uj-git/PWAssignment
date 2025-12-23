package com.test.pwassignment.domain.model


import com.google.gson.annotations.SerializedName

data class Quiz(
    @SerializedName("attempts")
    val attempts: Int
)