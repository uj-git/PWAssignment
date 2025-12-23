package com.test.pwassignment.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

// 1. Define the Custom Shape
class BellCurveShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            // Start at Bottom Left
            moveTo(0f, size.height)

            // First half of the curve (Bottom Left to Top Center)
            // Uses cubic bezier to create a smooth "S" curve
            cubicTo(
                x1 = size.width * 0.25f,
                y1 = size.height, // Control Point 1: Keeps it flat at start
                x2 = size.width * 0.25f,
                y2 = 0f,          // Control Point 2: Pulls it up
                x3 = size.width * 0.5f,
                y3 = 0f            // End Point: Top Center
            )

            // Second half of the curve (Top Center to Bottom Right)
            cubicTo(
                x1 = size.width * 0.75f, y1 = 0f,          // Control Point 1: Keeps it flat at top
                x2 = size.width * 0.75f, y2 = size.height, // Control Point 2: Pulls it down
                x3 = size.width, y3 = size.height          // End Point: Bottom Right
            )

            // Close the path along the bottom edge
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}

// 2. Usage Composable
@Composable
fun CurveButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .width(200.dp)
            .height(56.dp)
            .background(Color.Black, shape = BellCurveShape())
            .clickable(true) {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCurve() {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(Color.White)
    ) {
        CurveButton("Sign In") {

        }
    }
}