package br.com.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import br.com.calculadora.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.operator.Operator
import net.objecthunter.exp4j.operator.Operators


class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val PARENS = "parens"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.num0.setOnClickListener(){
            binding.calc.text = "${binding.calc.text}0"
        }
        binding.num1.setOnClickListener(){
            binding.calc.text = "${binding.calc.text}1"
        }
        binding.num2.setOnClickListener(){
            binding.calc.text = "${binding.calc.text}2"
        }
        binding.num3.setOnClickListener(){
            binding.calc.text = "${binding.calc.text}3"
        }
        binding.num4.setOnClickListener(){
            binding.calc.text = "${binding.calc.text}4"
        }
        binding.num5.setOnClickListener(){
            binding.calc.text = "${binding.calc.text}5"
        }
        binding.num6.setOnClickListener(){
            binding.calc.text = "${binding.calc.text}6"
        }
        binding.num7.setOnClickListener(){
            binding.calc.text = "${binding.calc.text}7"
        }
        binding.num8.setOnClickListener(){
            binding.calc.text = "${binding.calc.text}8"
        }
        binding.num9.setOnClickListener(){
            binding.calc.text = "${binding.calc.text}9"
        }
        binding.plus.setOnClickListener(){
            addSymbol("+")
        }
        binding.min.setOnClickListener(){
            addSymbol("-")
        }
        binding.times.setOnClickListener(){
            addSymbol("×")
        }
        binding.div.setOnClickListener(){
            addSymbol("÷")
        }
        binding.parens.setOnClickListener(){
            addSymbol(PARENS)
        }
        binding.percent.setOnClickListener(){
        }
        binding.comma.setOnClickListener(){
            addSymbol(",")
        }
        binding.backspace.setOnClickListener(){
            binding.calc.text = binding.calc.text.dropLast(1)
        }
        binding.allClear.setOnClickListener(){
            binding.calc.text = ""
            binding.result.text = ""
        }

        binding.equal.setOnClickListener(){
            val calc = binding.calc.text.toString().replace('÷','/').replace('×','*').replace(',', '.')
            val expression = ExpressionBuilder(calc).build()
            binding.result.text = formatResult(expression.evaluate())
        }

    }

    private fun addSymbol(symbol: String) {
        val calcText = binding.calc.text.toString()

        if (calcText.contains('(') && symbol == PARENS){
            binding.calc.text = calcText.plus(')')
        } else if (symbol == PARENS) {
            binding.calc.text = calcText.plus('(')
        } else if (Operator.isAllowedOperatorChar(calcText.last())) {
            binding.calc.text = calcText.substring(0, calcText.length - 1).plus(symbol)
        } else {
            binding.calc.text = calcText.plus(symbol)
        }

    }

    private fun formatResult(value: Double): String {
        val formatted = if (value % 1 == 0.0) {
            value.toInt().toString()
        } else {
            String.format("%.10f", value).trimEnd('0', '.')
        }
        return formatted.replace('.',',')
    }


}