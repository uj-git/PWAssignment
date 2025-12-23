package com.test.pwassignment.domain.model


import com.google.gson.annotations.SerializedName

data class Accuracy(
    @SerializedName("current")
    val current: String
)