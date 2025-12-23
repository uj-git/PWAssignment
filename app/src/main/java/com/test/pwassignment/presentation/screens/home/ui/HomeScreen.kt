package com.test.pwassignment.presentation.screens.home.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.pwassignment.R
import com.test.pwassignment.domain.model.QuizStreak
import com.test.pwassignment.domain.model.StudentData
import com.test.pwassignment.presentation.components.CustomComponents.CustomCardWithPadding
import com.test.pwassignment.presentation.components.CustomComponents.ErrorContent
import com.test.pwassignment.presentation.components.CustomComponents.LoadingDialog
import com.test.pwassignment.presentation.components.PWColors
import com.test.pwassignment.presentation.screens.home.view_model.HomeUiState
import com.test.pwassignment.presentation.screens.home.view_model.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    homeVM: HomeViewModel = koinViewModel(),
    onNotificationClick: () -> Unit
) {
    val uiState by homeVM.uiState.collectAsState()

    Scaffold { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(PWColors.White)
        ) {

            // 🔹 CONTENT
            when {
                uiState.data != null -> {
                    HomeContent(
                        uiState = uiState.data!!,
                        onNotificationClick = {
                            onNotificationClick()
                        }
                    )
                }

                uiState.error != null -> {
                    ErrorContent(
                        message = uiState.error ?: "Something went wrong",
                        onRetry = { homeVM.loadDashboard() }
                    )
                }
            }

            // 🔹 LOADING DIALOG (on top of everything)
            LoadingDialog(show = uiState.isLoading)
        }
    }
}

@Composable
fun HomeContent(
    uiState: StudentData,
    onNotificationClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        StudentNameAndClassSection(
            uiState.student.name,
            uiState.student.className,
            onNotificationClick = {
                onNotificationClick()
            }
        )

        AttributeCards(
            availabilityStatus = uiState.student.availability.status,
            accuracy = uiState.student.accuracy.current,
            quizAttempts = uiState.student.quiz.attempts.toString()
        )

        Text(
            text = "Today's Summary",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = PWColors.Black
        )

        SummaryCard(
            mood = uiState.todaySummary.mood,
            description = uiState.todaySummary.description,
            watchTitle = uiState.todaySummary.recommendedVideo.actionText
        )

        Text(
            text = "Weekly Overview",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = PWColors.Black
        )

        WeeklyOverViewCard(
            list = uiState.weeklyOverview.quizStreak,
            progressValue = uiState.weeklyOverview.overallAccuracy.percentage.toFloat(),
            accuracyValue = uiState.weeklyOverview.overallAccuracy.label
        )
    }
}


@Composable
fun StudentNameAndClassSection(
    name: String,
    className: String,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(
                8.dp,
                alignment = Alignment.CenterVertically
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(
                text = "Hello $name!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = PWColors.Black
            )

            Text(
                text = className,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = PWColors.BodyGrey
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.bell_icon),
            contentDescription = "Notification",
            tint = PWColors.Black,
            modifier = Modifier.clickable {
                onNotificationClick()
            }
        )
    }
}

@Composable
fun AttributeCards(
    modifier: Modifier = Modifier,
    availabilityStatus: String,
    accuracy: String,
    quizAttempts: String
) {
    Row(
        horizontalArrangement = Arrangement.Absolute.SpaceAround,
        modifier = modifier.fillMaxWidth()
    ) {
        CustomCardWithPadding(
            modifier = Modifier.weight(1f),
            borderWidth = 1.dp,
            borderColor = PWColors.GreenPrimary,
            backgroundColor = PWColors.GreenSecondary,
            content = {
                AttributesData(
                    title = "Availability",
                    value = availabilityStatus,
                    valueColor = PWColors.GreenBold,
                    icon = R.drawable.attendance_icon,
                    iconTint = Color.Unspecified
                )
            }
        )
        Spacer(modifier = Modifier.width(16.dp))

        CustomCardWithPadding(
            modifier = Modifier.weight(1f),
            borderWidth = 1.dp,
            borderColor = PWColors.OrangeBorder,
            backgroundColor = PWColors.OrangeSurface,
            content = {
                AttributesData(
                    title = "Quiz",
                    value = "$quizAttempts Attempt",
                    valueColor = PWColors.Black,
                    icon = R.drawable.quiz_icon,
                    iconTint = Color.Unspecified
                )
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        CustomCardWithPadding(
            modifier = Modifier.weight(1f),
            borderWidth = 1.dp,
            borderColor = PWColors.RedBorder,
            backgroundColor = PWColors.RedSurface,
            content = {
                AttributesData(
                    title = "Accuracy",
                    value = accuracy,
                    valueColor = PWColors.Black,
                    icon = R.drawable.accuracy_icon,
                    iconTint = Color.Unspecified
                )
            }
        )

    }
}

@Composable
fun AttributesData(
    icon: Int,
    iconTint: Color,
    title: String,
    value: String,
    valueColor: Color
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterVertically),
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = iconTint
        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Normal,
            color = PWColors.Black
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = valueColor
        )
    }
}

@Composable
fun SummaryCard(
    mood: String,
    description: String,
    watchTitle: String
) {
    CustomCardWithPadding(
        modifier = Modifier.fillMaxWidth(),
        borderWidth = 1.dp,
        borderColor = PWColors.PurpleBorder,
        backgroundColor = PWColors.PurpleSurface,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        16.dp,
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.focused_icon),
                        contentDescription = null
                    )

                    Text(
                        text = mood,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = PWColors.PurpleBorder
                    )

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        color = PWColors.Black
                    )

                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = PWColors.Black, shape = MaterialTheme.shapes.medium)
                            .padding(4.dp),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PWColors.Black,
                            contentColor = PWColors.White
                        )
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                8.dp,
                                alignment = Alignment.CenterHorizontally
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.chevron_right_filled),
                                contentDescription = null,
                                tint = PWColors.White
                            )

                            Text(
                                text = watchTitle,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun WeeklyOverViewCard(
    modifier: Modifier = Modifier,
    list: List<QuizStreak>,
    progressValue: Float,
    accuracyValue: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = PWColors.Black,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .background(
                color = PWColors.White,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .padding(16.dp)
        ,
        colors = CardDefaults.cardColors(
            containerColor = PWColors.White
        ),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            WeeklyOverviewItems(
                title = "Quiz Streak",
                icon = R.drawable.question_cards_icon
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                list.forEach { item ->
                    DayCircleItem(
                        modifier = Modifier.size(40.dp),
                        day = item.day,
                        status = item.status
                    )
                }
            }

            WeeklyOverviewItems(
                title = "Accuracy",
                icon = R.drawable.target_icon
            )

            Text(
                text = accuracyValue,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = PWColors.Black
            )


            RoundedLinearProgressBar(
                progressValue = progressValue
            )


            WeeklyOverviewItems(
                title = "Performance by Topic",
                icon = R.drawable.graph_icon
            )
        }
    }
}


@Composable
fun WeeklyOverviewItems(
    modifier: Modifier = Modifier,
    title: String,
    icon: Int
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = PWColors.Black
            )

            Image(
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            PWColors.LinearGradientColor1,
                            PWColors.LinearGradientColor2
                        ),
                        start = Offset(0f, 0f),
                        end = Offset.Infinite
                    )

                )
        )
    }
}


@Composable
fun DayCircleItem(
    day: String,
    status: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        // Show done icon if status is "done"
        if (status == "done") {
            Image(
                painter = painterResource(id = R.drawable.done_circle_filled),
                contentDescription = "Completed",
                modifier = Modifier.matchParentSize()
            )
        } else {
            // Show perforated circle for all days
            PerforatedCircle(
                modifier = Modifier.matchParentSize(),
                color = Color.DarkGray,
                strokeWidth = 4f,
                dashIntervals = floatArrayOf(4f, 8f) // Adjust for desired dotted effect
            )

            // Display day text
            Text(
                text = day,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}

@Composable
fun PerforatedCircle(
    modifier: Modifier = Modifier,
    color: Color = Color.DarkGray,
    strokeWidth: Float = 4f,
    dashIntervals: FloatArray = floatArrayOf(4f, 8f)
) {
    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2f
        val center = Offset(x = size.width / 2f, y = size.height / 2f)

        drawCircle(
            color = color,
            center = center,
            radius = radius,
            style = Stroke(
                width = strokeWidth,
                pathEffect = PathEffect.dashPathEffect(
                    intervals = dashIntervals,
                    phase = 0f
                ),
                cap = StrokeCap.Round
            )
        )
    }
}

@Composable
fun RoundedLinearProgressBar(
    progressValue: Float, // backend value (e.g. 68.0)
    modifier: Modifier = Modifier,
    height: Dp = 8.dp
) {
    // Convert percentage → 0f..1f
    val progress = (progressValue / 100f).coerceIn(0f, 1f)

    LinearProgressIndicator(
        progress = { progress },
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(50)),
        color = Color(0xFFFF6776),
        trackColor = Color(0xFFFFE0E3),
        strokeCap = StrokeCap.Round
    )
}


