package com.tft_mvvm.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.item_show_by_gold.view.*

@Suppress("CAST_NEVER_SUCCEEDS")
class AdapterShowByGold(
    private val champs: ArrayList<ItemViewHolder.ChampByGold>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(champ: ChampByGold, onItemClickListener: OnItemClickListener) {
            itemView.name.text = champ.name
            Glide.with(itemView.img_show_by_gold.context)
                .load(champ.linkImg)
                .into(itemView.img_show_by_gold)
            when (champ.cost) {
                "1" -> itemView.img_show_by_gold.setBackgroundResource(R.drawable.background_1_gold)
                "2" -> itemView.img_show_by_gold.setBackgroundResource(R.drawable.background_2_gold)
                "3" -> itemView.img_show_by_gold.setBackgroundResource(R.drawable.background_3_gold)
                "4" -> itemView.img_show_by_gold.setBackgroundResource(R.drawable.background_4_gold)
                "5" -> itemView.img_show_by_gold.setBackgroundResource(R.drawable.background_5_gold)
            }
            itemView.setOnClickListener { onItemClickListener.onClickListener(champ.id) }
        }

        data class ChampByGold(
            val name: String,
            val linkImg: String,
            val cost: String,
            val id: String
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_show_by_gold, parent, false)
        )

    override fun getItemCount(): Int = champs.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as ItemViewHolder).bind(champs[position], onItemClickListener)

    fun addData(list: List<ItemViewHolder.ChampByGold>) {
        champs.clear()
        champs.addAll(list)
        notifyDataSetChanged()
    }
}