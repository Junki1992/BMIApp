package com.websarva.wings.android.bmiapp

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_calc.*
import java.util.*

class CalcActivity : AppCompatActivity() {

    private val tag = "BMIList"
    private lateinit var bmiList: BMIList
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
            deleteButton.visibility = View.VISIBLE
            mailButton.visibility = View.VISIBLE
        } else {
            deleteButton.visibility = View.INVISIBLE
            mailButton.visibility = View.INVISIBLE
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
            } else {
                finish()
            }
        }

        mailButton.setOnClickListener {
            onSendMassage()
        }

        deleteButton.setOnClickListener {
            onDelete()
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
                Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
                finish()
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
                Toast.makeText(applicationContext, "修正しました", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun onSendMassage() {
        realm.executeTransaction {
            bmiList = realm.where<BMIList>()
                .equalTo("id", bmiId)
                .findFirst()!!
            bmiList.dateTime
            bmiList.height
            bmiList.weight
            bmiList.bmi
        }
        val subject = getString(R.string.app_subject)
        val text = "$bmiList"
        val uri = Uri.fromParts("mailto", "junki.warner@me.com", null)
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(intent)
    }

    private fun onDelete() {
        dialog = AlertDialog.Builder(this@CalcActivity).apply {
            setTitle("レコードの削除")
            setMessage("選択したレコードを削除しますか？")
            setPositiveButton("はい") { dialog, which ->
                realm.executeTransaction {
                    val bmiList = realm.where<BMIList>()
                        .equalTo("id", bmiId)
                        ?.findFirst()
                        ?.deleteFromRealm()
                }
                Toast.makeText(applicationContext, "削除しました", Toast.LENGTH_SHORT).show()
                finish()
            }
            setNegativeButton("いいえ") { dialog, which ->
                Toast.makeText(applicationContext, "中止しました", Toast.LENGTH_SHORT).show()
            }
            show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}



