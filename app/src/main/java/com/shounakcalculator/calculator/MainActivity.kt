package com.shounakcalculator.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.shounakcalculator.calculator.databinding.ActivityMainBinding
import com.shounakcalculator.calculator.history.HistoryDialogFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val calculatorViewModel: CalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            viewModel = calculatorViewModel
            lifecycleOwner = this@MainActivity
            setContentView(root)
        }

        calculatorViewModel.error.observe(this) {
            if (it != null) Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        setupObservers()
    }

    private fun setupObservers() {
        calculatorViewModel.showHistory.observe(this) {
            if (it.getContentIfNotHandled() != null) {
                HistoryDialogFragment().show(supportFragmentManager, "history-fragment")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.viewModel?.getLastCalculations(this)
    }
}
