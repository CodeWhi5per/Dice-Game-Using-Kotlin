package com.example.dice_game_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceGameApp()
        }
    }
}

@Composable
fun DiceGameApp() {
    var showGameScreen by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showTargetSetScreen by remember { mutableStateOf(false) }
    var targetScore by remember { mutableStateOf(101) }

    if (showGameScreen) {
        GameScreen(onBack = { showGameScreen = false }, targetScore = targetScore)
    } else if (showTargetSetScreen) {
        TargetSetScreen(
            onCancel = { showTargetSetScreen = false },
            onOk = { newTarget ->
                targetScore = newTarget
                showTargetSetScreen = false
                showGameScreen = true
            }
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Image(
                painter = painterResource(id = R.drawable.dice_app_background),
                contentDescription = "Dice App Background",
                modifier = Modifier
                    .size(410.dp)
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

    if (showAboutDialog) {
        AboutDialog(onDismiss = { showAboutDialog = false })
    }
}

@Composable
fun AboutDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .height(500.dp),
            shape = MaterialTheme.shapes.medium,
            color = Color(0xFF262626)
        ) {
            Column(
                modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Danushka Nandasena\nW1959858",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(35.dp))
                Text(
                    text = "I confirm that I understand what plagiarism is and have read and understood the section on Assessment Offences in the Essential Information for Students. The work that I have submitted is entirely my own. Any work from other authors is duly referenced and acknowledged.",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Justify,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00E5FF),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .height(60.dp)
                        .width(150.dp)
                ) {
                    Text(
                        text = "CLOSE",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun GameScreen(onBack: () -> Unit, targetScore: Int) {
    var humanScore by remember { mutableStateOf(0) }
    var computerScore by remember { mutableStateOf(0) }
    var humanDice by remember { mutableStateOf(List(5) { Random.nextInt(1, 7) }) }
    var computerDice by remember { mutableStateOf(List(5) { Random.nextInt(1, 7) }) }
    var rollsLeft by remember { mutableStateOf(3) }
    var selectedDice by remember { mutableStateOf<List<Int>>(emptyList()) }
    var gameOver by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "H: $humanScore / C: $computerScore", fontSize = 18.sp)
            Text(text = "Target: $targetScore", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Human Dice:", fontSize = 16.sp)
        DiceRow(diceValues = humanDice, selectedDice = selectedDice, onDiceSelected = { index ->
            selectedDice = if (index in selectedDice) selectedDice - index else selectedDice + index
        })
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Computer Dice:", fontSize = 16.sp)
        DiceRow(diceValues = computerDice, selectedDice = emptyList(), onDiceSelected = {})
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                if (rollsLeft > 0) {
                    humanDice = humanDice.mapIndexed { index, value ->
                        if (index in selectedDice) value else Random.nextInt(1, 7)
                    }
                    computerDice = computerDice.map { Random.nextInt(1, 7) }
                    rollsLeft--
                }
            }, enabled = rollsLeft > 0) {
                Text("Throw ($rollsLeft left)")
            }
            Button(onClick = {
                humanScore += humanDice.sum()
                computerScore += computerDice.sum()
                if (humanScore >= targetScore || computerScore >= targetScore) {
                    gameOver = true
                } else {
                    humanDice = List(5) { Random.nextInt(1, 7) }
                    computerDice = List(5) { Random.nextInt(1, 7) }
                    rollsLeft = 3
                    selectedDice = emptyList()
                }
            }) {
                Text("Score")
            }
        }
        if (gameOver) {
            val winner = if (humanScore >= targetScore && humanScore > computerScore) "You win!" else "You lose!"
            val color = if (winner == "You win!") Color.Green else Color.Red
            Dialog(onDismissRequest = {}) {
                Surface(
                    modifier = Modifier.padding(16.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = winner, fontSize = 24.sp, color = color)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onBack) {
                            Text("Back to Main Menu")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DiceRow(diceValues: List<Int>, selectedDice: List<Int>, onDiceSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        diceValues.forEachIndexed { index, value ->
            DiceImage(
                value = value,
                isSelected = index in selectedDice,
                onClick = { onDiceSelected(index) }
            )
        }
    }
}

@Composable
fun DiceImage(value: Int, isSelected: Boolean, onClick: () -> Unit) {
    val imageRes = when (value) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_1
    }
    Box(
        modifier = Modifier
            .size(64.dp)
            .padding(4.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Dice $value",
            modifier = Modifier.size(64.dp)
        )
        if (isSelected) {
            Text(
                text = "✓",
                modifier = Modifier.align(Alignment.TopEnd),
                color = Color.Green,
                fontSize = 20.sp
            )
        }
    }
}


@Composable
fun TargetSetScreen(onCancel: () -> Unit, onOk: (Int) -> Unit) {
    var target by remember { mutableStateOf("101") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "TARGET",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = target,
                onValueChange = { target = it },
                shape = MaterialTheme.shapes.large,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(fontSize = 24.sp),
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF212121),
                    unfocusedContainerColor = Color(0xFF212121),
                    disabledContainerColor = Color(0xFF212121),
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(50.dp)) // Add this spacer to shift the "CANCEL" button to the right
                Button(
                    onClick = onCancel,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .height(45.dp)
                        .width(110.dp)
                ) {
                    Text(
                        text = "CANCEL",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    onClick = { onOk(target.toIntOrNull() ?: 101) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00E5FF),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .height(45.dp)
                        .width(110.dp)
                ) {
                    Text(
                        text = "OK",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(50.dp))
            }
        }
    }
}