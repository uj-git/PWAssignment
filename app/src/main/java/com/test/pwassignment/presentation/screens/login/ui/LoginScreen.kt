package com.test.pwassignment.presentation.screens.login.ui

import android.graphics.fonts.FontFamily
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.test.pwassignment.R
import com.test.pwassignment.presentation.components.CurveButton
import com.test.pwassignment.presentation.screens.login.ui.view_model.LoginUiEvent
import com.test.pwassignment.presentation.screens.login.ui.view_model.LoginUiState
import com.test.pwassignment.presentation.screens.login.ui.view_model.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    // 🔥 Handle one-time events
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is LoginUiEvent.Success -> {
                    onLoginSuccess()
                }
                is LoginUiEvent.Error -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Black)
        ) {

            // ---- YOUR EXISTING UI ----

            LoginCardContent(
                uiState = uiState,
                onSchoolIdChange = viewModel::onSchoolIdChange,
                onStudentIdChange = viewModel::onStudentIdChange,
                onSignInClick = viewModel::onSignInClick
            )
        }
    }
}

@Composable
fun LoginCardContent(
    uiState: LoginUiState,
    onSchoolIdChange: (String) -> Unit,
    onStudentIdChange: (String) -> Unit,
    onSignInClick: () -> Unit
) {

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        // 🔹 Background
        LoginBackground()

        // 🔹 Bottom Card
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(36.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Let’s Get you Signed in",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    )

                    // 🔹 School ID
                    AppOutlineTextField(
                        value = uiState.schoolId,
                        placeholderText = "School ID",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = onSchoolIdChange
                    )

                    // 🔹 Student ID
                    AppOutlineTextField(
                        value = uiState.studentId,
                        placeholderText = "Student ID",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = onStudentIdChange
                    )

                    // 🔹 CTA Button
                    CurveButton(
                        text = if (uiState.isLoading) "Signing In..." else "Sign In",
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        onSignInClick()
                    }
                }
            }
        }
    }
}

@Composable
private fun AppOutlineTextField(
    value: String,
    placeholderText: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        placeholder = {
            Text(
                text = placeholderText,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color("#1B2124".toColorInt())
                )
            )
        },
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color("#1B2124".toColorInt())
        ),
        shape = RoundedCornerShape(22.dp),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color("#D9DCE1".toColorInt()),
            focusedBorderColor = Color("#757575".toColorInt())
        )
    )
}

@Composable
private fun LoginBackground() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(R.drawable.bg),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
        )

        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(R.drawable.top_creative),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            text = "Welcome to\n Quizzy!",
            color = Color.White,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 40.sp,
                letterSpacing = 0.sp, // 0% mapping
                textAlign = TextAlign.Center
            )
        )
    }
}


