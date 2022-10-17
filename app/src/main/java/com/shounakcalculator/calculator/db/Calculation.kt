package com.shounakcalculator.calculator.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Calculation(
   @PrimaryKey(autoGenerate = true) val id: Int = 0,
   val equation: String,
   val answer: String
)