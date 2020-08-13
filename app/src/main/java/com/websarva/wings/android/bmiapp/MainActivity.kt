package com.websarva.wings.android.bmiapp

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btClick = findViewById<Button>(R.id.button)
        val listener = CalucButtonListener()
        btClick.setOnClickListener(listener)

    }
    //ボタンをクリックした時のリスナクラス
    private inner class CalucButtonListener : View.OnClickListener {
        override fun onClick(view: View) {
            //身長を表示するeditTextオブジェクトを取得
            val input = findViewById<EditText>(R.id.editHeight).text.toString().toFloat()
            //体重を取得するeditText2オブジェクト取得
            val input2 = findViewById<EditText>(R.id.editWeight).text.toString().toFloat()
            //身長を変換(cm → m)
            val met = input / 100
            //BMIを計算
            val bmi = input2 / (met * met)
            //val toast = Toast.makeText(applicationContext, "%,.1f".format(bmi), Toast.LENGTH_LONG)
            // 位置調整
            //toast.setGravity(Gravity.TOP, -350, 1100)
            //toast.show()
            //計算結果を表示
            textViewBMI.text = ("%,.1f".format(bmi))
        }
    }
}


