package com.test.pwassignment.domain.model


import com.google.gson.annotations.SerializedName

data class OverallAccuracy(
    @SerializedName("label")
    val label: String,
    @SerializedName("percentage")
    val percentage: Int
)