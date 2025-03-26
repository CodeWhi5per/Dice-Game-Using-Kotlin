package com.example.dice_game_assignment

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun AboutDialog(onDismiss: () -> Unit) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .height(if (isLandscape) 250.dp else 500.dp),
            shape = MaterialTheme.shapes.medium,
            color = Color(0xFF262626)
        ) {
            Column(
                modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Danushka Nandasena\nW1959858",
                    fontSize = if (isLandscape) 15.sp else 25.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(if (isLandscape) 5.dp else 35.dp))
                Text(
                    text = "I confirm that I understand what plagiarism is and have read and understood the section on Assessment Offences in the Essential Information for Students. The work that I have submitted is entirely my own. Any work from other authors is duly referenced and acknowledged.",
                    fontSize = if (isLandscape) 12.sp else 20.sp,
                    textAlign = TextAlign.Justify,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(if (isLandscape) 1.dp else 20.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00E5FF),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .height(if (isLandscape) 50.dp else 60.dp)
                        .width(if (isLandscape) 85.dp else 150.dp)
                ) {
                    Text(
                        text = "CLOSE",
                        fontSize = if (isLandscape) 9.sp else 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}