package br.com.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import br.com.calculadora.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.operator.Operator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val parens = "( )"
    private val parenOpen = '('
    private val parenClose = ')'
    private val firstSymbolAllowed = arrayOf("-", ",")
    private val operators = arrayOf("+", "-", "÷", "×", "%", "=")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttons = listOf(
            binding.num0,
            binding.num1,
            binding.num2,
            binding.num3,
            binding.num4,
            binding.num5,
            binding.num6,
            binding.num7,
            binding.num8,
            binding.num9,
            binding.div,
            binding.times,
            binding.min,
            binding.plus,
            binding.percent,
            binding.parens,
            binding.comma,
            binding.equal,
            binding.backspace,
            binding.allClear
        )

        for (button in buttons) {
            button.setOnClickListener() {
                val buttonText = when (button) {
                    is Button -> button.text.toString()
                    else -> ""
                }

                if (isInt(buttonText)) {
                    binding.calc.text = binding.calc.text.toString().plus(buttonText)
                    calculateResult()
                } else {
                    when {
                        buttonText == "AC" -> {
                            binding.calc.text = ""
                            binding.result.text = ""
                        }
                        buttonText.isEmpty() -> {
                            backspace()
                            calculateResult()
                        }
                        buttonText == "=" && binding.calc.text.isNotEmpty() -> calculateResult()
                        else -> symbolClickListener(buttonText)
                    }
                }
            }
        }
    }

    private fun isInt(char: String): Boolean {
        return char.toIntOrNull() != null
    }

    private fun isOperator(char: String): Boolean {
        return operators.contains(char)
    }

    private fun backspace (){
        if (binding.calc.text.isNotEmpty())
            binding.calc.text = binding.calc.text.dropLast(1)
        else updateDisplayCalc("")
    }

    private fun symbolClickListener(buttonText: String) {
        var lastChar = ""
        var displayCalcText = ""
        var displayFirstOperator = false

        if (binding.calc.text.isNotEmpty()) {
            displayCalcText = binding.calc.text.toString()
            lastChar = binding.calc.text.last().toString()
        }

        firstSymbolAllowed.forEach { op ->
            if (op == displayCalcText) displayFirstOperator = true
        }
        val countParentOpen = displayCalcText.count { it == parenOpen }
        val countParentClose = displayCalcText.count { it == parenClose }

        return if (displayCalcText.isEmpty() && firstSymbolAllowed.contains(buttonText)){
            updateDisplayCalc(buttonText)
        }
        else {
             if (buttonText == parens) {
                if ((displayCalcText.contains(parenOpen) && isInt(lastChar.toString())) ||
                    (lastChar == parenClose.toString() && countParentOpen > countParentClose)) {
                    updateDisplayCalc(parenClose.toString())
                } else {
                    updateDisplayCalc(parenOpen.toString())
                }
            }
            else if (!displayFirstOperator && (isOperator(lastChar) || lastChar.toString() == buttonText) && isOperator(buttonText)) {
                backspace()
                updateDisplayCalc(buttonText)
            }
            else if (displayCalcText.isNotEmpty() && !displayFirstOperator) {
                updateDisplayCalc(buttonText)
            } else {
                // No action needed, binding.calc.text remains the same
            }
        }
    }

    private fun updateDisplayCalc(upCalc: String){
        binding.calc.text = binding.calc.text.toString().plus(upCalc)
    }

    private fun calculateResult() {
        if (binding.calc.text.isNotEmpty()){
            val startsWithOperator = operators.contains(binding.calc.text.first().toString())

            if ((startsWithOperator && binding.calc.text.count {operators.contains(it.toString())} >= 2) ||
                (!startsWithOperator && binding.calc.text.count {operators.contains(it.toString())} >= 1)){

                var expression = binding.calc.text.toString()
                    .replace('÷','/')

                // ignore the operator at the end
                if (expression.isNotEmpty() && Operator.isAllowedOperatorChar(expression.last())){
                    expression = expression.dropLast(1)
                }

                val expressionBuilder = ExpressionBuilder(expression).build()

                if (expressionBuilder.validate().isValid){
                    val result = formatResult(expressionBuilder.evaluate())

                    if (result != expression) binding.result.text = result
                    else binding.result.text = ""
                 }
            }
        } else binding.result.text = ""
    }

    private fun formatResult(value: Double): CharSequence? {
        val formatted = if (value % 1 == 0.0) {
            value.toInt().toString()
        } else {
            String.format("%.10f", value).trimEnd('0', '.')
        }
        return formatted.replace('.',',')
    }
}