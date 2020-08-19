package com.websarva.wings.android.bmiapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.Date

open class BMIList : RealmObject() {
    @PrimaryKey
    var id: Long = 0L
    var dateTime: Date = Date()
    var height: Float = 0F
    var weight: Float = 0F
    var bmi: Float = 0F
}