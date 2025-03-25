package com.example.dice_game_assignment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DiceGameApp() {
    var showGameScreen by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showTargetSetScreen by remember { mutableStateOf(false) }
    var targetScore by remember { mutableIntStateOf(101) }

    when {
        showTargetSetScreen -> {
            TargetSetScreen(
                onCancel = { showTargetSetScreen = false },
                onOk = { newTarget ->
                    targetScore = newTarget
                    showTargetSetScreen = false
                    showGameScreen = true
                }
            )
        }
        showGameScreen -> {
            GameScreen(targetScore, onBack = { showGameScreen = false })
        }
        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dice_app_background),
                    contentDescription = "Dice App Background",
                    modifier = Modifier
                        .size(400.dp)
                        .align(Alignment.TopCenter)
                        .padding(top = 280.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .padding(bottom = 130.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { showTargetSetScreen = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00E5FF),
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .height(60.dp)
                            .width(200.dp)
                    ) {
                        Text(
                            text = "NEW GAME",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showAboutDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .height(60.dp)
                            .width(200.dp)
                    ) {
                        Text(
                            text = "ABOUT",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    if (showAboutDialog) {
        AboutDialog(onDismiss = { showAboutDialog = false })
    }
}