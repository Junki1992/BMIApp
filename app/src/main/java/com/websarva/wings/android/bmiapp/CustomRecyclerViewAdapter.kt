package com.websarva.wings.android.bmiapp

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults
import android.text.format.DateFormat

class CustomRecyclerViewAdapter(realmResults: RealmResults<BMIList>) : RecyclerView.Adapter<ViewHolder>() {
    private val rResults: RealmResults<BMIList> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_result, parent, false)
        val viewholder = ViewHolder(view)
        return viewholder
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bmiList = rResults[position]
        holder.dateText?.text = DateFormat.format("yyyy/MM/dd kk:mm", bmiList?.dateTime)
        holder.heightText?.text = "${bmiList?.height.toString()}"+" cm"
        holder.weightText?.text = "${bmiList?.weight.toString()}"+" kg"
        holder.bmiText?.text = bmiList?.bmi.toString()
        holder.itemView.setBackgroundColor(if (position % 2 == 0) Color.LTGRAY else Color.WHITE)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, CalcActivity::class.java)
            intent.putExtra("id", bmiList?.id)
            it.context.startActivity(intent)
        }
    }
}