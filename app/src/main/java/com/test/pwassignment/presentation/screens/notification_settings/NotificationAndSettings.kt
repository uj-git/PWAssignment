package com.test.pwassignment.presentation.screens.notification_settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.test.pwassignment.R
import com.test.pwassignment.presentation.components.PWColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationAndSettings(
    onNavBack: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize().background(color = PWColors.White).padding(horizontal = 16.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Notifications & Settings")
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.chevron_left_outllined),
                        contentDescription = null,
                        tint = PWColors.Black,
                        modifier = Modifier.clickable {
                            onNavBack()
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PWColors.White,
                    titleContentColor = PWColors.Black
                )
            )
        },
        containerColor = PWColors.White
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize().background(Color.Transparent),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top)
        ) {
            Text(
                text = "Notifications",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = PWColors.Black
            )

            Notifications()

            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = PWColors.Black
            )

            SettingOptions(
                onClick = {
                    onLogout()
                }
            )
        }
    }
}


@Composable
fun Notifications() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)

    ) {
        NotificationItem(
            indicatorColor = PWColors.OrangeBorder,
            title = "Missed quiz in physics in yesterday",
            subTitle = "2 hours ago",
            backgroundColor = PWColors.OrangeSurface
        )
        NotificationItem(
            indicatorColor = PWColors.PurpleBorder,
            title = "Badge earned",
            subTitle = "8 hours ago",
            backgroundColor = PWColors.PurpleSurface
        )
        NotificationItem(
            indicatorColor = PWColors.GreenPrimary,
            title = "Teacher Note",
            subTitle = "1 day ago",
            backgroundColor = PWColors.GreenSecondary
        )

    }
}

@Composable
fun NotificationItem(
    modifier : Modifier = Modifier,
    indicatorColor: Color,
    backgroundColor: Color,
    title: String,
    subTitle: String
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(color = backgroundColor)
        ,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IndicatorBox(color = indicatorColor)

        TitleAndSubtitleTextColumn(
            title = title,
            subTitle = subTitle,
            backgroundColor = backgroundColor
        )
    }
}

@Composable
fun SettingOptions(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        SettingsOption(
            title = "Switch Child",
            subTitle = "Change active child profile",
            icon = R.drawable.users_icon
        )
        SettingsOption(
            title = "Language",
            subTitle = "English",
            icon = R.drawable.web_icon
        )
        SettingsOption(
            title = "Logout",
            subTitle = "Sign out of your account",
            icon = R.drawable.logout_icon,
            onClick = {
                onClick()
            }
        )

    }
}


@Composable
fun SettingsOption(
    title: String,
    subTitle: String,
    icon : Int,
    iconTint: Color = Color.Unspecified,
    onClick: () -> Unit = {}
    ) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable{
            onClick()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = iconTint
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = PWColors.Black
            )

            Text(
                text = subTitle,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                color = PWColors.BodyGrey
            )
        }
    }
}

@Composable
fun TabContent(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    indicatorColor: Color,
    backgroundColor: Color
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        IndicatorBox(color = indicatorColor)

        TitleAndSubtitleTextColumn(
            title = title,
            subTitle = subTitle
        )
    }
}

@Composable
fun IndicatorBox(modifier: Modifier = Modifier, color: Color) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(4.dp)
            .background(
                color = color
            )

    )
}

@Composable
fun TitleAndSubtitleTextColumn(
    title: String,
    subTitle: String,
    backgroundColor: Color = PWColors.White,
    verticalDistance: Dp = 4.dp,
    verticalPadding: Dp = 8.dp
) {
    Column(
        modifier = Modifier.fillMaxWidth().background(color = backgroundColor).padding(verticalPadding),
        verticalArrangement = Arrangement.spacedBy(verticalDistance, Alignment.CenterVertically),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = PWColors.Black
        )

        Text(
            text = subTitle,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Normal,
            color = PWColors.BodyGrey
        )
    }
}
