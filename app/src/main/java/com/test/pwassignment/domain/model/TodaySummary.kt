package com.test.pwassignment.domain.model


import com.google.gson.annotations.SerializedName

data class TodaySummary(
    @SerializedName("characterImage")
    val characterImage: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("mood")
    val mood: String,
    @SerializedName("recommendedVideo")
    val recommendedVideo: RecommendedVideo
)