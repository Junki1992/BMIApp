package com.websarva.wings.android.bmiapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_calc.*
import java.util.Date

class CalcActivity : AppCompatActivity() {

    private val tag = "BMIList"
    private lateinit var realm: Realm
    private lateinit var dialog: AlertDialog.Builder
    var bmiId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)
        realm = Realm.getDefaultInstance()

        bmiId = intent.getLongExtra("id", 0L)
        if (bmiId > 0L) {
            val bmiList = realm.where<BMIList>()
                .equalTo("id", bmiId).findFirst()
            editHeight.setText(bmiList?.height.toString())
            editWeight.setText(bmiList?.weight.toString())
            textViewBMI.text = bmiList?.bmi.toString()
        }

        calcButton.setOnClickListener {
            onCalc()
        }

        clearButton.isEnabled =
            !(editHeight.text.toString() == "" && editWeight.text.toString() == "")

        saveButton.setOnClickListener {
            if (editHeight.text.toString() == "" || editWeight.text.toString() == "") {
                Toast.makeText(applicationContext, "計算を完了させて下さい", Toast.LENGTH_SHORT).show()
            } else {
                onSave()
            }
        }

        clearButton.setOnClickListener {
            editHeight.editableText.clear()
            editWeight.editableText.clear()
            textViewBMI.text = ""
        }

        backButton.setOnClickListener {
            if (editHeight.text.toString() == "" || editWeight.text.toString() == "") {
                dialog = AlertDialog.Builder(this@CalcActivity).apply {
                    setTitle("測定を中止")
                    setMessage("BMI測定を中止しますか？")
                    setPositiveButton("はい") {dialog, which ->
                        finish()
                    }
                    setNegativeButton("いいえ") {dialog, which ->}
                        show()
                }
            }
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
            clearButton.isEnabled = true
        }
    }

    private fun onSave() {
        var height: Float = 0F
        var weight: Float = 0F
        var bmi: Float = 0F
        if (!editHeight.text.isNullOrEmpty()) {
            height = editHeight.text.toString().toFloat()
        }
        if (!editWeight.text.isNullOrEmpty()) {
            weight = editWeight.text.toString().toFloat()
        }
        if (!textViewBMI.text.isNullOrEmpty()) {
            bmi = textViewBMI.text.toString().toFloat()
        }

        when(bmiId) {
            0L -> {
                realm.executeTransaction {
                    val maxId = realm.where<BMIList>().max("id")
                    val nextId = (maxId?.toLong() ?: 0L) + 1L
                    val bmiList = realm.createObject<BMIList>(nextId)
                    bmiList.dateTime = Date()
                    bmiList.height = height
                    bmiList.weight = weight
                    bmiList.bmi = bmi
                }
            }
            //修正処理
            else -> {
                realm.executeTransaction {
                    val bmiList = realm.where<BMIList>()
                        .equalTo("id", bmiId).findFirst()
                    bmiList?.height = height
                    bmiList?.weight = weight
                    bmiList?.bmi = bmi
                }
            }
        }
        Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}



