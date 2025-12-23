package com.test.pwassignment.domain.model


import com.google.gson.annotations.SerializedName

data class PerformanceByTopic(
    @SerializedName("topic")
    val topic: String,
    @SerializedName("trend")
    val trend: String
)