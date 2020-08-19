package com.websarva.wings.android.bmiapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_result.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var dateText: TextView? = null
    var heightText: TextView? = null
    var weightText: TextView? = null
    var bmiText: TextView? = null
    init {
        //ビューホルダーのプロパティとレイアウトのViewの対応
        dateText = itemView.dateText
        heightText = itemView.heightText
        weightText = itemView.weightText
        bmiText = itemView.bmiText
    }
}