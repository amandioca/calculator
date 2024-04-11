package br.com.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import br.com.calculadora.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
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
            binding.calc.text = "${binding.calc.text}+"
        }
        binding.min.setOnClickListener(){
            binding.calc.text = "${binding.calc.text}-"
        }
        binding.times.setOnClickListener(){
            binding.calc.text = "${binding.calc.text}×"
        }
        binding.div.setOnClickListener(){
            binding.calc.text = "${binding.calc.text}÷"
        }
        binding.parensOpen.setOnClickListener(){
            binding.calc.text = "${binding.calc.text}("
        }
        binding.parensClose.setOnClickListener(){
            binding.calc.text = "${binding.calc.text})"
        }
        binding.comma.setOnClickListener(){
            binding.calc.text = "${binding.calc.text},"
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

    private fun formatResult(value: Double): String {
        val formatted = if (value % 1 == 0.0) {
            value.toInt().toString()
        } else {
            String.format("%.10f", value).trimEnd('0', '.')
        }
        return formatted.replace('.',',')
    }
}