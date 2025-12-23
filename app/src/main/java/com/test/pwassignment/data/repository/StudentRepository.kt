package com.test.pwassignment.data.repository

import com.test.pwassignment.data.remote.api.ApiService

class StudentRepository(
    private val apiService: ApiService
) {

    suspend fun fetchDashboard() =
        apiService.getStudentDashboard()
}
