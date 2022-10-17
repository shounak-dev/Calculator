package com.shounakcalculator.calculator

import android.app.Application
import androidx.room.Room
import com.shounakcalculator.calculator.db.CalculationDatabase

class CalculatorApplication : Application() {

    private lateinit var calculationsDatabase: CalculationDatabase

    override fun onCreate() {
        super.onCreate()

        calculationsDatabase = Room.databaseBuilder(
            applicationContext,
            CalculationDatabase::class.java, "calculations"
        ).build()
    }

    fun getDatabaseInstance(): CalculationDatabase {
        return calculationsDatabase
    }

}