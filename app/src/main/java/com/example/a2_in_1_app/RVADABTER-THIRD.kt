package com.example.a2_in_1_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row_third.view.*

class RVAdapterThird (val numbers: ArrayList<String>): RecyclerView.Adapter<RVAdapterThird.numberListHolder>() {
    class numberListHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): numberListHolder {
        return numberListHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row_third,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: numberListHolder, position: Int) {
        val number = numbers[position]
        holder.itemView.apply {
            tvItem2.text = number.toString()
        }}

    override fun getItemCount(): Int = numbers.size
}