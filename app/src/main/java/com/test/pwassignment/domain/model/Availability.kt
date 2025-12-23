package com.test.pwassignment.domain.model


import com.google.gson.annotations.SerializedName

data class Availability(
    @SerializedName("status")
    val status: String
)