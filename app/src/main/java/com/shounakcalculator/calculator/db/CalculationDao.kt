package com.shounakcalculator.calculator.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CalculationDao {

    @Insert
    suspend fun storeCalculation(calculation: Calculation)

    @Query("SELECT * FROM calculation")
    suspend fun getAllCalculations(): List<Calculation>

}