package com.websarva.wings.android.bmiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

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
            val input = findViewById<EditText>(R.id.editText).text.toString().toFloat()
            //体重を取得するeditText2オブジェクト取得
            val input2 = findViewById<EditText>(R.id.editText2).text.toString().toFloat()
            //身長を変換(cm → m)
            val met = input / 100
            //BMIを計算
            val bmi = input2 / (met * met)
            val toast = Toast.makeText(applicationContext, "%,.1f".format(bmi), Toast.LENGTH_LONG)
            // 位置調整
//            toast.setGravity(Gravity.CENTER, x, y)
            toast.show()
        }
    }
}


