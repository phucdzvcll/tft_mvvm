package com.tft_mvvm.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.item_bonus_of_class_or_origin.view.*

class AdapterShowBonusOfClassOrOrigin(
    private val list: ArrayList<String>
) : RecyclerView.Adapter<AdapterShowBonusOfClassOrOrigin.ViewViewHolder>() {

    class ViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(string: String) {
            if (string.isEmpty()) {
                itemView.item_bonus_count.visibility = View.GONE
                itemView.item_bonus.visibility = View.GONE
            } else {
                val item = string.split(":")
                itemView.item_bonus_count.text = item[0]
                itemView.item_bonus.text = item[1]
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bonus_of_class_or_origin, parent, false)
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun addData(listBonus: List<String>) {
        list.clear()
        list.addAll(listBonus)
        notifyDataSetChanged()
    }
}