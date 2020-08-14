package com.websarva.wings.android.bmiapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class BMIList : RealmObject() {
    @PrimaryKey
    var id = 0
    var height = 0
    var weight = 0
    var bmi = 0
}