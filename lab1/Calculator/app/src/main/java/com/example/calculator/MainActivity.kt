package com.example.calculator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var bindingClass: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(getLayoutInflater())
        setContentView(bindingClass.root)

        val math_op = intent.getStringExtra(Constans.math)
        val res = intent.getStringExtra(Constans.result)

        bindingClass.mathOperation.text = math_op
        bindingClass.result.text = res

        bindingClass.zeroBtn.setOnClickListener{setTextFields("0")}
        bindingClass.oneBtn.setOnClickListener{setTextFields("1")}
        bindingClass.twoBtn.setOnClickListener{setTextFields("2")}
        bindingClass.threeBtn.setOnClickListener{setTextFields("3")}
        bindingClass.fourBtn.setOnClickListener{setTextFields("4")}
        bindingClass.fiveBtn.setOnClickListener{setTextFields("5")}
        bindingClass.sixBtn.setOnClickListener{setTextFields("6")}
        bindingClass.sevenBtn.setOnClickListener{setTextFields("7")}
        bindingClass.eightBtn.setOnClickListener{setTextFields("8")}
        bindingClass.nineBtn.setOnClickListener{setTextFields("9")}

        bindingClass.plusBtn.setOnClickListener{setTextFields("+")}
        bindingClass.minusBtn.setOnClickListener{setTextFields("-")}
        bindingClass.divisionBtn.setOnClickListener{setTextFields("/")}
        bindingClass.mulBtn.setOnClickListener{setTextFields("*")}
        bindingClass.openPtn.setOnClickListener{setTextFields("(")}
        bindingClass.closeBtn.setOnClickListener{setTextFields(")")}
        bindingClass.pointBtn.setOnClickListener{setTextFields(".")}

        bindingClass.abBtn.setOnClickListener{
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
            catch(e:Exception)
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
        val  shift = Intent(this, SecondActiivity::class.java)
        shift.putExtra(Constans.math, bindingClass.mathOperation.text.toString())
        shift.putExtra(Constans.result, bindingClass.result.text.toString())
        startActivity(shift)
    }


}