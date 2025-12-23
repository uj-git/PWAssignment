package com.test.pwassignment.di

import com.test.pwassignment.data.remote.RetrofitProvider
import com.test.pwassignment.data.repository.StudentRepository
import com.test.pwassignment.presentation.screens.home.view_model.HomeViewModel
import com.test.pwassignment.presentation.screens.login.ui.view_model.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitProvider.provideApiService() }

    single { StudentRepository(get()) }

    viewModel { HomeViewModel(get()) }

    viewModel { LoginViewModel() }
}
