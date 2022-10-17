package com.shounakcalculator.calculator

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shounakcalculator.calculator.db.Calculation
import com.shounakcalculator.calculator.utils.EventForLiveData
import kotlinx.coroutines.launch
import org.mariuszgromada.math.mxparser.Expression
import java.text.DecimalFormat

class CalculatorViewModel :
    ViewModel() {

    private val _showHistory = MutableLiveData<EventForLiveData<Unit>>()
    private val _lastCalculations = MutableLiveData<List<Calculation>>(emptyList())
    private val _inputDisplayText = MutableLiveData("")
    private val _outputDisplayText = MutableLiveData("")
    private val _error = MutableLiveData<String?>()


    val showHistory: LiveData<EventForLiveData<Unit>>
        get() = _showHistory

    val lastCalculations: LiveData<List<Calculation>>
        get() = _lastCalculations

    val inputDisplayText: LiveData<String>
        get() = _inputDisplayText
    val outputDisplayText: LiveData<String>
        get() = _outputDisplayText
    val error: LiveData<String?>
        get() = _error

    fun insertDigit(digit: String) {
        var value = _inputDisplayText.value
        value += digit
        _inputDisplayText.value = value
    }

    fun clear() {
        _inputDisplayText.value = ""
        _outputDisplayText.value = ""
    }

    fun backspace() {
        val backspace: String?

        val inputText = _inputDisplayText.value!!
        if (inputText.isNotEmpty()) {
            var check = 0
            val stringBuilder = StringBuilder(inputText)
            val find = inputText.last()

            if (find == '+' || find == '-' || find == '*' || find == '/') {
                check -= 1
            }
            stringBuilder.deleteCharAt(inputText.length - 1)
            backspace = stringBuilder.toString()
            _inputDisplayText.value = backspace
        }
        _outputDisplayText.value = ""
    }

    fun result(context: Context) {
        try {
            val expression = getInputExpression(inputDisplayText.value)
            val result = Expression(expression).calculate()
            if (result.isNaN()) {
                _error.value = "Please Enter Valid Numbers"
            } else {
                _error.value = null
                _outputDisplayText.value = DecimalFormat("0.######").format(result).toString()
                saveCalculationToDatabase(context, expression, result)
            }
        } catch (e: Exception) {
            _error.value = "Please Enter Valid Numbers"
        }
    }

    private fun getInputExpression(expression: String?): String? {
        return expression
            ?.replace(Regex("÷"), "/")
            ?.replace(Regex("×"), "*")
            ?.replace(Regex("%"), "/100*")
    }

    private fun saveCalculationToDatabase(context: Context, expression: String?, result: Double) {
        if (expression != null) {
            viewModelScope.launch {
                (context.applicationContext as CalculatorApplication).getDatabaseInstance()
                    .calculationDao()
                    .storeCalculation(
                        Calculation(
                            equation = expression,
                            answer = result.toString()
                        )
                    )
                getLastCalculations(context)
            }
        }
    }

    fun getLastCalculations(context: Context) {
        viewModelScope.launch {
            _lastCalculations.postValue(
                (context.applicationContext as CalculatorApplication).getDatabaseInstance()
                    .calculationDao()
                    .getAllCalculations().takeLast(5).reversed()
            )
        }
    }

    fun showHistory() {
        _showHistory.postValue(EventForLiveData(Unit))
    }
}