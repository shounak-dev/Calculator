package com.shounakcalculator.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.shounakcalculator.calculator.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import org.mariuszgromada.math.mxparser.Expression
import java.lang.StringBuilder
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var check = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonclear.setOnClickListener {
            binding.input.text = ""
            binding.output.text = ""
        }
        binding.buttonzero.setOnClickListener {
            binding.input.append("0")
        }
        binding.buttonone.setOnClickListener {
            binding.input.append("1")
        }
        binding.buttontwo.setOnClickListener {
            binding.input.append("2")
        }
        binding.buttonthree.setOnClickListener {
            binding.input.append("3")
        }
        binding.buttonfour.setOnClickListener {
            binding.input.append("4")
        }
        binding.buttonfive.setOnClickListener {
            binding.input.append("5")
        }
        binding.buttonsix.setOnClickListener {
            binding.input.append("6")
        }
        binding.buttonseven.setOnClickListener {
            binding.input.append("7")
        }
        binding.buttoneight.setOnClickListener {
            binding.input.append("8")
        }
        binding.buttonnine.setOnClickListener {
            binding.input.append("9")
        }
        binding.buttonb1.setOnClickListener {
            binding.input.append("(")
        }
        binding.buttonb2.setOnClickListener {
            binding.input.append(")")
        }
        binding.buttondot.setOnClickListener {
            binding.input.append(".")
        }
        binding.buttonplus.setOnClickListener {
            binding.input.append(" + ")
        }
        binding.buttonminus.setOnClickListener {
            binding.input.append(" - ")
        }
        binding.buttonmultiply.setOnClickListener {
            binding.input.append(" x ")
        }
        binding.buttondivision.setOnClickListener {
            binding.input.append(" ÷ ")
        }
        binding.buttonpercentage.setOnClickListener{
            binding.input.append(" % ")
        }
        binding.buttonbackspace.setOnClickListener {
            var backspace: String? = null
            if (binding.input.text.isNotEmpty()) {
                val stringBuilder: StringBuilder = StringBuilder(binding.input.text)
                val find = binding.input.text.toString()
                val find2 = find.last()

                if (find2.equals('+') || find2.equals('-') || find2.equals('*') || find2.equals('/')) {
                    check = check - 1
                }
                stringBuilder.deleteCharAt(binding.input.text.length - 1)
                backspace = stringBuilder.toString()
                binding.input.setText(backspace)
            }
            binding.output.text = ""
        }
        binding.buttonequal.setOnClickListener {
            try {
                val expression = getInputExpression()
                val result = Expression(expression).calculate()
                if (result.isNaN()) {
                    Toast.makeText(this, "Please Enter Valid Numbers", Toast.LENGTH_SHORT).show()
                } else {
                    output.text = DecimalFormat("0.######").format(result).toString()
                    output.setTextColor(ContextCompat.getColor(this, R.color.green))
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Please Enter Valid Numbers", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getInputExpression(): String {
        var expression = input.text.replace(Regex("÷"), "/")
        expression = expression.replace(Regex("×"), "*")
        expression = expression.replace(Regex("%"),"/100*")
        return expression
    }
}
