package com.websarva.wings.android.bmiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //realm = Realm.getDefaultInstance()
        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, CalcActivity::class.java)
            startActivity(intent)
        }
    }
}