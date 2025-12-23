package com.test.pwassignment.domain.model


import com.google.gson.annotations.SerializedName

data class RecommendedVideo(
    @SerializedName("actionText")
    val actionText: String,
    @SerializedName("title")
    val title: String
)