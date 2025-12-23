package com.test.pwassignment.domain.model


import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("accuracy")
    val accuracy: Accuracy,
    @SerializedName("availability")
    val availability: Availability,
    @SerializedName("class")
    val className: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("quiz")
    val quiz: Quiz
)