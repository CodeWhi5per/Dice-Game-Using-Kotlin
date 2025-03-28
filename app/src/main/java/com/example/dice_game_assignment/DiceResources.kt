package com.example.dice_game_assignment

import androidx.annotation.DrawableRes

object DiceResources {
    @DrawableRes
    fun getDiceResource(dice: Int, isRightSide: Boolean = false): Int {
        // Return the appropriate drawable resource based on the dice value and side
        return if (isRightSide) {
            when (dice) {
                1 -> R.drawable.white_dice_1
                2 -> R.drawable.white_dice_2
                3 -> R.drawable.white_dice_3
                4 -> R.drawable.white_dice_4
                5 -> R.drawable.white_dice_5
                6 -> R.drawable.white_dice_6
                else -> R.drawable.white_dice_1 // Default to dice 1 if value is out of range
            }
        } else {
            when (dice) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                6 -> R.drawable.dice_6
                else -> R.drawable.dice_1 // Default to dice 1 if value is out of range
            }
        }
    }
}