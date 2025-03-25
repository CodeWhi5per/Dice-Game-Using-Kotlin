package com.example.dice_game_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
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
            GameScreen(targetScore)
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

@Composable
fun GameScreen(targetScore: Int) {
    var leftDiceImages by remember { mutableStateOf(List(5) { (1..6).random() }) }
    var rightDiceImages by remember { mutableStateOf(List(5) { (1..6).random() }) }
    var leftDiceSum by remember { mutableStateOf(0) }
    var rightDiceSum by remember { mutableStateOf(0) }
    var rollCount by remember { mutableStateOf(0) }
    var humanTotalScore by remember { mutableStateOf(0) }
    var computerTotalScore by remember { mutableStateOf(0) }
    var selectedDice by remember { mutableStateOf(List(5) { false }) }
    var computerSelectedDice by remember { mutableStateOf(List(5) { false }) }

    fun updateScores() {
        leftDiceSum = leftDiceImages.sum()
        rightDiceSum = rightDiceImages.sum()
        humanTotalScore += leftDiceSum
        computerTotalScore += rightDiceSum
        selectedDice = List(5) { false } // Clear the red borders
        computerSelectedDice = List(5) { false } // Clear the green borders
        leftDiceImages = List(5) { (1..6).random() } // Reset human dice
    }

    fun rerollDice() {
        leftDiceImages = leftDiceImages.mapIndexed { index, dice ->
            if (selectedDice[index]) dice else (1..6).random()
        }
        rightDiceImages = List(5) { (1..6).random() }
    }

    fun computerRerollDice() {
        // Randomly decide whether to reroll
        val reroll = (0..1).random() == 1
        if (reroll) {
            // Randomly decide which dice to keep
            computerSelectedDice = List(5) { (0..1).random() == 1 }
            rightDiceImages = rightDiceImages.mapIndexed { index, dice ->
                if (computerSelectedDice[index]) dice else (1..6).random()
            }
        } else {
            computerSelectedDice = List(5) { false }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "WIN COUNT",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "5",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "3",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "SCORE",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "H : $humanTotalScore",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "C : $computerTotalScore",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 100.dp)
                .background(Color(0xFF262626), shape = RoundedCornerShape(16.dp))
                .width(20.dp)
                .height(60.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$targetScore",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 70.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user_icon),
                    contentDescription = "User",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                leftDiceImages.forEachIndexed { index, dice ->
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .border(
                                width = 2.dp,
                                color = if (selectedDice[index]) Color.Red else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                selectedDice = selectedDice.toMutableList().apply {
                                    this[index] = !this[index]
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = getDiceResource(dice)),
                            contentDescription = "Dice $dice"
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 70.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.computer_icon),
                    contentDescription = "Computer",
                    modifier = Modifier.size(45.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                rightDiceImages.forEachIndexed { index, dice ->
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .border(
                                width = 2.dp,
                                color = if (computerSelectedDice[index]) Color.Green else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = getDiceResource(dice)),
                            contentDescription = "Dice $dice"
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Button(
                onClick = {
                    rerollDice()
                    computerRerollDice()
                    rollCount++
                    if (rollCount == 3) {
                        updateScores()
                        rollCount = 0
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5722),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .height(40.dp)
                    .width(120.dp)
            ) {
                Text(
                    text = "THROW",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = {
                    while (rollCount < 3) {
                        computerRerollDice()
                        rollCount++
                    }
                    updateScores()
                    rollCount = 0
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF08FF00),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .height(40.dp)
                    .width(120.dp)
            ) {
                Text(
                    text = "SCORE",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

fun getDiceResource(dice: Int): Int {
    return when (dice) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        7 -> R.drawable.dice_7
        8 -> R.drawable.dice_8
        9 -> R.drawable.dice_9
        10 -> R.drawable.dice_10
        11 -> R.drawable.dice_11
        12 -> R.drawable.dice_12
        else -> R.drawable.dice_1
    }
}