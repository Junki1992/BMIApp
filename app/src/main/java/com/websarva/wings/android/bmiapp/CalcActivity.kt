package com.websarva.wings.android.bmiapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calc.*

class CalcActivity : AppCompatActivity() {

//    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)
//        realm = Realm.getDefaultInstance()
        calcButton.setOnClickListener {
            onCalc()
        }

        saveButton.setOnClickListener {
            if (editHeight.text.toString() == "" || editWeight.text.toString() == "") {
                Toast.makeText(applicationContext, "計算を完了させて下さい", Toast.LENGTH_SHORT).show()
            }
        }

        clearButton.setOnClickListener {
            editHeight.editableText.clear()
            editWeight.editableText.clear()
            textViewBMI.text = ""
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun onCalc() {
        if (editHeight.text.toString() == "" || editWeight.text.toString() == "") {
            Toast.makeText(applicationContext, "身長と体重を入力してください", Toast.LENGTH_SHORT).show()
        } else {
            val inputHeight = editHeight.text.toString().toFloat()
            val inputWeight = editWeight.text.toString().toFloat()
            val met = inputHeight / 100
            val bmi = inputWeight / (met * met)
            textViewBMI.text = ("%,.1f".format(bmi))
            textViewBMI.setTextColor(Color.BLACK)
        }
    }
}



