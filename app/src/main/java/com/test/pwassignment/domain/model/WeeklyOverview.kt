package com.test.pwassignment.domain.model


import com.google.gson.annotations.SerializedName

data class WeeklyOverview(
    @SerializedName("overallAccuracy")
    val overallAccuracy: OverallAccuracy,
    @SerializedName("performanceByTopic")
    val performanceByTopic: List<PerformanceByTopic>,
    @SerializedName("quizStreak")
    val quizStreak: List<QuizStreak>
)