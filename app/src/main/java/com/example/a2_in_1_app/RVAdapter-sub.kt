package com.example.a2_in_1_app


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row_sub.view.*

class RVAdapterSub(val Items: ArrayList<String>) :
    RecyclerView.Adapter<RVAdapterSub.numberListHolder>() {
    class numberListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): numberListHolder {
        return numberListHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row_sub,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: numberListHolder, position: Int) {
        val number = Items[position]
        holder.itemView.apply {
            tvItem.text = number.toString()
        }
    }

    override fun getItemCount(): Int = Items.size
}