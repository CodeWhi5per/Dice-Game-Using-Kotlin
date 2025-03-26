package com.example.dice_game_assignment

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.compose.BackHandler
import com.example.dice_game_assignment.DiceResources.getDiceResource
import kotlinx.coroutines.*

@Composable
fun GameScreen(
    targetScore: Int,
    humanWinCount: Int,
    computerWinCount: Int,
    onBack: () -> Unit,
    onWinUpdate: (Int, Int) -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

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
    var humanAttempts by remember { mutableIntStateOf(0) }
    var computerAttempts by remember { mutableIntStateOf(0) }
    var showDice by remember { mutableStateOf(false) }

    BackHandler {
        onBack()
    }

    fun updateScores() {
        showDice = true
        leftDiceImages = leftDiceImages.mapIndexed { index, dice ->
            if (selectedDice[index]) dice else (1..6).random()
        }
        leftDiceSum = leftDiceImages.sum()
        rightDiceSum = rightDiceImages.sum()
        humanTotalScore += leftDiceSum
        computerTotalScore += rightDiceSum
        selectedDice = List(5) { false }
        computerSelectedDice = List(5) { false }
        humanAttempts++
        computerAttempts++

        if (humanTotalScore >= targetScore && computerTotalScore >= targetScore) {
            if (humanAttempts == computerAttempts) {
                if (humanTotalScore > computerTotalScore) {
                    winMessage = "YOU WIN!"
                    winMessageColor = Color.Black
                    showWinDialog = true
                    gameOver = true
                    currentHumanWinCount++
                } else if (computerTotalScore > humanTotalScore) {
                    winMessage = "YOU LOSE!"
                    winMessageColor = Color.Black
                    showWinDialog = true
                    gameOver = true
                    currentComputerWinCount++
                } else {
                    leftDiceImages = List(5) { (1..6).random() }
                    rightDiceImages = List(5) { (1..6).random() }
                    leftDiceSum = leftDiceImages.sum()
                    rightDiceSum = rightDiceImages.sum()
                    if (leftDiceSum > rightDiceSum) {
                        winMessage = "YOU WIN!"
                        winMessageColor = Color.Black
                        showWinDialog = true
                        gameOver = true
                        currentHumanWinCount++
                    } else if (rightDiceSum > leftDiceSum) {
                        winMessage = "YOU LOSE!"
                        winMessageColor = Color.Black
                        showWinDialog = true
                        gameOver = true
                        currentComputerWinCount++
                    } else {
                        updateScores()
                    }
                }
            } else if (humanAttempts < computerAttempts) {
                winMessage = "YOU WIN!"
                winMessageColor = Color.Black
                showWinDialog = true
                gameOver = true
                currentHumanWinCount++
            } else {
                winMessage = "YOU LOSE!"
                winMessageColor = Color.Black
                showWinDialog = true
                gameOver = true
                currentComputerWinCount++
            }
        } else if (humanTotalScore >= targetScore) {
            winMessage = "YOU WIN!"
            winMessageColor = Color.Black
            showWinDialog = true
            gameOver = true
            currentHumanWinCount++
        } else if (computerTotalScore >= targetScore) {
            winMessage = "YOU LOSE!"
            winMessageColor = Color.Black
            showWinDialog = true
            gameOver = true
            currentComputerWinCount++
        }

        onWinUpdate(currentHumanWinCount, currentComputerWinCount)
    }

    fun rerollDice() {
        showDice = true
        leftDiceImages = leftDiceImages.mapIndexed { index, dice ->
            if (selectedDice[index]) dice else (1..6).random()
        }
        rightDiceImages = List(5) { (1..6).random() }
    }

    // ---Strategy Explaination---

//    Score Difference Calculation: calculate the difference between the human's total score and the computer's total score.
//    Significantly Lower Score: If the computer's score is significantly lower (by more than 20 points), it rerolls the dice with values less than 4 to try to catch up.
//    Significantly Higher Score: If the computer's score is significantly higher (by more than 20 points), it rerolls the dice with values greater than 3 to maintain its lead.
//    Random Decision: If the scores are close, the computer randomly decides whether to reroll or not.


//    ADVANTAGES:

//    *** Adaptive Strategy: The computer adapts its strategy based on the current score difference, making it more competitive.
//    *** Risk Management: By rerolling only the lowest or highest dice based on the situation, the computer manages its risk effectively.

//    DISADVANTAGES:

//    *** Randomness: The random decision in close score situations may not always be optimal.
//    *** Fixed Threshold: The threshold of 20 points for significant score difference is arbitrary and may not be optimal for all game scenarios.


    fun computerRerollDice() {
        // Calculate the score difference
        val scoreDifference = humanTotalScore - computerTotalScore

        // If the computer's total score is significantly lower than the human's total score
        if (scoreDifference > 10) {
            // Reroll to catch up with the high dice
            computerSelectedDice = rightDiceImages.map { it < 4 }
            rightDiceImages = rightDiceImages.mapIndexed { index, dice ->
                if (computerSelectedDice[index]) (1..6).random() else dice
            }
        }
        // If the computer's total score is significantly higher than the human's total score
        else if (scoreDifference < -10) {
            // Reroll with the lowest dice
            computerSelectedDice = rightDiceImages.map { it > 3 }
            rightDiceImages = rightDiceImages.mapIndexed { index, dice ->
                if (computerSelectedDice[index]) (1..6).random() else dice
            }
        }
        // Otherwise, decide randomly whether to reroll or not
        else {
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
                .padding(horizontal = if (isLandscape) 15.dp else 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "WIN COUNT",
                    color = Color.White,
                    fontSize = if (isLandscape) 12.sp else 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(if (isLandscape) 35.dp else 70.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "H : $currentHumanWinCount",
                            color = Color.Black,
                            fontSize = if (isLandscape) 9.sp else 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box(
                        modifier = Modifier
                            .width(if (isLandscape) 35.dp else 70.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "C : $currentComputerWinCount",
                            color = Color.Black,
                            fontSize = if (isLandscape) 9.sp else 13.sp,
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
                    fontSize = if (isLandscape) 12.sp else 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(if (isLandscape) 35.dp else 70.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "H : $humanTotalScore",
                            color = Color.Black,
                            fontSize = if (isLandscape) 9.sp else 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box(
                        modifier = Modifier
                            .width(if (isLandscape) 35.dp else 70.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "C : $computerTotalScore",
                            color = Color.Black,
                            fontSize = if (isLandscape) 9.sp else 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(if (isLandscape) 20.dp else 40.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = if (isLandscape) 300.dp else 100.dp)
                .background(Color(0xFF262626), shape = RoundedCornerShape(16.dp))
                .width(20.dp)
                .height(if (isLandscape) 30.dp else 60.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$targetScore",
                color = Color.White,
                fontSize = if (isLandscape) 20.sp else 40.sp,
                fontWeight = FontWeight.Black
            )
        }
        Spacer(modifier = Modifier.height(if (isLandscape) 20.dp else 40.dp))
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 35.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user_icon),
                        contentDescription = "User",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    if (showDice) {
                        leftDiceImages.forEachIndexed { index, dice ->
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
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
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 35.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.computer_icon),
                        contentDescription = "Computer",
                        modifier = Modifier.size(22.5.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    if (showDice) {
                        rightDiceImages.forEachIndexed { index, dice ->
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
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
            }
        } else {
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
                    if (showDice) {
                        leftDiceImages.forEachIndexed { index, dice ->
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
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
                    if (showDice) {
                        rightDiceImages.forEachIndexed { index, dice ->
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
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
                    .height(if (isLandscape) 30.dp else 40.dp)
                    .width(if (isLandscape) 100.dp else 120.dp),
                enabled = !gameOver
            ) {
                Text(
                    text = "THROW",
                    fontSize = if (isLandscape) 9.sp else 18.sp,
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
                    .height(if (isLandscape) 30.dp else 40.dp)
                    .width(if (isLandscape) 100.dp else 120.dp),
                enabled = !gameOver
            ) {
                Text(
                    text = "SCORE",
                    fontSize = if (isLandscape) 9.sp else 18.sp,
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (isLandscape) 30.dp else 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = winMessage,
                        color = Color.Black,
                        fontSize = if (isLandscape) 20.sp else 40.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            },
            containerColor = if (winMessage == "YOU WIN!") Color.Green else Color.Red
        )
    }
}