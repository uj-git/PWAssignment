package com.test.pwassignment.domain.model


import com.google.gson.annotations.SerializedName

data class StudentData(
    @SerializedName("student")
    val student: Student,
    @SerializedName("todaySummary")
    val todaySummary: TodaySummary,
    @SerializedName("weeklyOverview")
    val weeklyOverview: WeeklyOverview
)