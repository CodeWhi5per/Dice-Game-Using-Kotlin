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
    // Get the current configuration to determine orientation
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .height(if (isLandscape) 250.dp else 500.dp), // Adjust height based on orientation
            shape = MaterialTheme.shapes.medium,
            color = Color(0xFF262626) // Background color of the dialog
        ) {
            Column(
                modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Danushka Nandasena\nW1959858",
                    fontSize = if (isLandscape) 15.sp else 25.sp, // Adjust font size based on orientation
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(if (isLandscape) 5.dp else 35.dp)) // Adjust spacer height based on orientation
                Text(
                    text = "I confirm that I understand what plagiarism is and have read and understood the section on Assessment Offences in the Essential Information for Students. The work that I have submitted is entirely my own. Any work from other authors is duly referenced and acknowledged.",
                    fontSize = if (isLandscape) 12.sp else 20.sp, // Adjust font size based on orientation
                    textAlign = TextAlign.Justify,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(if (isLandscape) 1.dp else 20.dp)) // Adjust spacer height based on orientation
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00E5FF), // Button background color
                        contentColor = Color.Black // Button text color
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .height(if (isLandscape) 50.dp else 60.dp) // Adjust button height based on orientation
                        .width(if (isLandscape) 85.dp else 150.dp) // Adjust button width based on orientation
                ) {
                    Text(
                        text = "CLOSE",
                        fontSize = if (isLandscape) 9.sp else 18.sp, // Adjust font size based on orientation
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}