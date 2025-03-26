package com.example.dice_game_assignment

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DiceGameApp() {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    var showGameScreen by rememberSaveable { mutableStateOf(false) }
    var showAboutDialog by rememberSaveable { mutableStateOf(false) }
    var showTargetSetScreen by rememberSaveable { mutableStateOf(false) }
    var targetScore by rememberSaveable { mutableStateOf(101) }
    var humanWinCount by rememberSaveable { mutableStateOf(0) }
    var computerWinCount by rememberSaveable { mutableStateOf(0) }

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
            GameScreen(
                targetScore = targetScore,
                humanWinCount = humanWinCount,
                computerWinCount = computerWinCount,
                onBack = { showGameScreen = false },
                onWinUpdate = { humanWins, computerWins ->
                    humanWinCount = humanWins
                    computerWinCount = computerWins
                }
            )
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
                        .size(if (isLandscape) 250.dp else 400.dp)
                        .align(Alignment.TopCenter)
                        .padding(top = if (isLandscape) 1.dp else 280.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .padding(bottom = if (isLandscape) 10.dp else 130.dp),
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
                            .height(if (isLandscape) 40.dp else 60.dp)
                            .width(if (isLandscape) 100.dp else 200.dp)
                    ) {
                        Text(
                            text = "NEW GAME",
                            fontSize = if (isLandscape) 9.sp else 18.sp,
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
                            .height(if (isLandscape) 40.dp else 60.dp)
                            .width(if (isLandscape) 100.dp else 200.dp)
                    ) {
                        Text(
                            text = "ABOUT",
                            fontSize = if (isLandscape) 9.sp else 18.sp,
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