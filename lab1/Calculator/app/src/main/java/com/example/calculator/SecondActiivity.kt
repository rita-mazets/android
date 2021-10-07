package com.example.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.databinding.ActivitySecondActiivityBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class SecondActiivity : AppCompatActivity() {

    lateinit var bindingClass: ActivitySecondActiivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivitySecondActiivityBinding.inflate(getLayoutInflater())
        setContentView(bindingClass.root)
        val math_op = intent.getStringExtra(Constans.math)
        val res = intent.getStringExtra(Constans.result)

        bindingClass.mathOperation.text = math_op
        bindingClass.result.text = res

        bindingClass.zeroBtn.setOnClickListener { setTextFields("0") }
        bindingClass.oneBtn.setOnClickListener { setTextFields("1") }
        bindingClass.twoBtn.setOnClickListener { setTextFields("2") }
        bindingClass.threeBtn.setOnClickListener { setTextFields("3") }
        bindingClass.fourBtn.setOnClickListener { setTextFields("4") }
        bindingClass.fiveBtn.setOnClickListener { setTextFields("5") }
        bindingClass.sixBtn.setOnClickListener { setTextFields("6") }
        bindingClass.sevenBtn.setOnClickListener { setTextFields("7") }
        bindingClass.eightBtn.setOnClickListener { setTextFields("8") }
        bindingClass.nineBtn.setOnClickListener { setTextFields("9") }

        bindingClass.plusBtn.setOnClickListener { setTextFields("+") }
        bindingClass.minusBtn.setOnClickListener { setTextFields("-") }
        bindingClass.divisionBtn.setOnClickListener { setTextFields("/") }
        bindingClass.mulBtn.setOnClickListener { setTextFields("*") }
        bindingClass.openBtn.setOnClickListener { setTextFields("(") }
        bindingClass.closeBtn.setOnClickListener { setTextFields(")") }
        bindingClass.pointBtn.setOnClickListener { setTextFields(".") }
        bindingClass.remainderBtn.setOnClickListener { setTextFields("%") }

        bindingClass.sinPtn.setOnClickListener { setTextFields("sin(") }
        bindingClass.cosBtn.setOnClickListener { setTextFields("cos(") }
        bindingClass.tanBtn.setOnClickListener { setTextFields("tan(") }

        bindingClass.arcsinPtn.setOnClickListener { setTextFields("asin(") }
        bindingClass.arccosBtn.setOnClickListener { setTextFields("acos(") }
        bindingClass.arctanBtn.setOnClickListener { setTextFields("atan(") }

        bindingClass.sqrtBtn.setOnClickListener { setTextFields("sqrt(") }
        bindingClass.csqrtBtn.setOnClickListener { setTextFields("cbrt(") }
        bindingClass.log2Btn.setOnClickListener { setTextFields("log2(") }
        bindingClass.log10Btn.setOnClickListener { setTextFields("log10(") }
        bindingClass.lnBtn.setOnClickListener { setTextFields("log(") }
        bindingClass.expBtn.setOnClickListener { setTextFields("exp(") }
        bindingClass.powBtn.setOnClickListener { setTextFields("^") }
        bindingClass.absBtn.setOnClickListener { setTextFields("abs(") }

        bindingClass.abBtn.setOnClickListener {
            bindingClass.mathOperation.text = ""
            bindingClass.result.text = ""
        }

        bindingClass.backBtn.setOnClickListener{
            var str: String = bindingClass.mathOperation.text.toString()
            if(str.isNotEmpty())
                bindingClass.mathOperation.text = str.substring(0, str.length - 1)

            bindingClass.result.text = ""
        }

        bindingClass.equalBtn.setOnClickListener{
            try{
                val ex = ExpressionBuilder(bindingClass.mathOperation.text.toString()).build()
                val result = ex.evaluate()

                val longRes = result.toLong()
                if(longRes.toDouble() == result)
                    bindingClass.result.text = longRes.toString()
                else
                    bindingClass.result.text = result.toString()

            }
            catch(e: Exception)
            {
                Log.d("Error", "message: ${e.message}")

                bindingClass.result.text = "Error!"

            }
        }
    }


    fun setTextFields(str:String)
    {
        if(bindingClass.result.text != "")
        {
            bindingClass.mathOperation.append(bindingClass.result.text)
            bindingClass.result.text = ""
        }
        bindingClass.mathOperation.append(str)
    }

    fun OnClickShift(view: View) {
        val  shift = Intent(this, MainActivity::class.java)
        shift.putExtra(Constans.math, bindingClass.mathOperation.text.toString())
        shift.putExtra(Constans.result, bindingClass.result.text.toString())
        startActivity(shift)
    }

}