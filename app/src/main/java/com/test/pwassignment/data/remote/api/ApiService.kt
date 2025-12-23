package com.test.pwassignment.data.remote.api

import com.test.pwassignment.domain.model.StudentData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("student_dashboard.json")
    suspend fun getStudentDashboard(
        @Query("alt") alt: String = "media",
        @Query("token") token: String = "0091b4c2-2ee2-4326-99cd-96d5312b34bd"
    ): StudentData
}
