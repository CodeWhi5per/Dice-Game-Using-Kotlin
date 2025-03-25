package com.example.dice_game_assignment
import com.example.dice_game_assignment.DiceResources.getDiceResource

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.compose.BackHandler
import kotlinx.coroutines.*

@Composable
fun GameScreen(
    targetScore: Int,
    humanWinCount: Int,
    computerWinCount: Int,
    onBack: () -> Unit,
    onWinUpdate: (Int, Int) -> Unit
) {
    var leftDiceImages by remember { mutableStateOf(List(5) { (1..6).random() }) }
    var rightDiceImages by remember { mutableStateOf(List(5) { (1..6).random() }) }
    var leftDiceSum by remember { mutableIntStateOf(0) }
    var rightDiceSum by remember { mutableIntStateOf(0) }
    var rollCount by remember { mutableIntStateOf(0) }
    var humanTotalScore by remember { mutableIntStateOf(0) }
    var computerTotalScore by remember { mutableIntStateOf(0) }
    var selectedDice by remember { mutableStateOf(List(5) { false }) }
    var computerSelectedDice by remember { mutableStateOf(List(5) { false }) }
    var showWinDialog by remember { mutableStateOf(false) }
    var winMessage by remember { mutableStateOf("") }
    var winMessageColor by remember { mutableStateOf(Color.White) }
    var gameOver by remember { mutableStateOf(false) }
    var currentHumanWinCount by remember { mutableIntStateOf(humanWinCount) }
    var currentComputerWinCount by remember { mutableIntStateOf(computerWinCount) }

    BackHandler {
        onBack()
    }

    fun updateScores() {
        leftDiceSum = leftDiceImages.sum()
        rightDiceSum = rightDiceImages.sum()
        humanTotalScore += leftDiceSum
        computerTotalScore += rightDiceSum
        selectedDice = List(5) { false } // Clear the red borders
        computerSelectedDice = List(5) { false } // Clear the green borders

        if (humanTotalScore >= targetScore) {
            winMessage = "You win!"
            winMessageColor = Color.Green
            showWinDialog = true
            gameOver = true
            currentHumanWinCount++
            onWinUpdate(currentHumanWinCount, currentComputerWinCount)
        } else if (computerTotalScore >= targetScore) {
            winMessage = "You lose"
            winMessageColor = Color.Red
            showWinDialog = true
            gameOver = true
            currentComputerWinCount++
            onWinUpdate(currentHumanWinCount, currentComputerWinCount)
        }
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

    if (showWinDialog) {
        LaunchedEffect(Unit) {
            delay(3000)
            showWinDialog = false
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
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$currentHumanWinCount",
                            color = Color.Black,
                            fontSize = 13.sp,
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
                            text = "$currentComputerWinCount",
                            color = Color.Black,
                            fontSize = 13.sp,
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
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
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
                            fontSize = 13.sp,
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
                            fontSize = 13.sp,
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
                            .size(65.dp)
                            .border(
                                width = 4.dp,
                                color = if (selectedDice[index]) Color.Green else Color.Transparent,
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
                            .size(65.dp)
                            .border(
                                width = 4.dp,
                                color = if (computerSelectedDice[index]) Color.Red else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = getDiceResource(dice, isRightSide = true)),
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
                    if (!gameOver) {
                        rerollDice()
                        computerRerollDice()
                        rollCount++
                        if (rollCount == 3) {
                            updateScores()
                            rollCount = 0
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5722),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .height(40.dp)
                    .width(120.dp),
                enabled = !gameOver
            ) {
                Text(
                    text = "THROW",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = {
                    if (!gameOver) {
                        while (rollCount < 3) {
                            computerRerollDice()
                            rollCount++
                        }
                        updateScores()
                        rollCount = 0
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF08FF00),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .height(40.dp)
                    .width(120.dp),
                enabled = !gameOver
            ) {
                Text(
                    text = "SCORE",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    if (showWinDialog) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {},
            text = {
                Text(
                    text = winMessage,
                    color = winMessageColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        )
    }
}