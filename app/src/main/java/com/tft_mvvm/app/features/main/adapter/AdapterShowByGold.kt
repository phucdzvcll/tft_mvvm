package com.tft_mvvm.app.features.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.base.OnItemClickListener
import com.tft_mvvm.app.features.main.model.Champ
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.item_show_by_gold.view.*

@Suppress("CAST_NEVER_SUCCEEDS")
class AdapterShowByGold(
    private val champs: ArrayList<Champ>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(champ: Champ, onItemClickListener: OnItemClickListener) {
            itemView.name.text = champ.name
            Glide.with(itemView.img_show_by_gold.context)
                .load(champ.imgUrl)
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

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_show_by_gold, parent, false)
        )

    override fun getItemCount(): Int = champs.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as ItemViewHolder).bind(champs[position], onItemClickListener)

    fun addData(list: List<Champ>) {
        champs.clear()
        champs.addAll(list)
        notifyDataSetChanged()
    }
}