package com.tft_mvvm.app.features.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.base.OnItemClickListener
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.item_show_by_origin_class.view.*

class AdapterShowChampInTeamBuilder(
    private val champs: ArrayList<Champ>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AdapterShowChampInTeamBuilder.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(champ: Champ, onItemClickListener: OnItemClickListener) {
            Glide.with(itemView.imgShowByOriginClass.context)
                .load(champ.imgUrl)
                .into(itemView.imgShowByOriginClass)
            when (champ.cost) {
                "1" -> itemView.imgShowByOriginClass.setBackgroundResource(R.drawable.background_1_gold)
                "2" -> itemView.imgShowByOriginClass.setBackgroundResource(R.drawable.background_2_gold)
                "3" -> itemView.imgShowByOriginClass.setBackgroundResource(R.drawable.background_3_gold)
                "4" -> itemView.imgShowByOriginClass.setBackgroundResource(R.drawable.background_4_gold)
                "5" -> itemView.imgShowByOriginClass.setBackgroundResource(R.drawable.background_5_gold)
            }
            if(champ.threeStar=="3"){
                itemView.three_start.visibility = View.VISIBLE
            }
            if (champ.itemSuitable.isNotEmpty()) {
                itemView.suitable_item_of_team1.visibility = View.VISIBLE
                Glide.with(itemView.suitable_item_of_team1.context)
                    .load(champ.itemSuitable[0].itemAvatar)
                    .into(itemView.suitable_item_of_team1)
                if (champ.itemSuitable.size >= 2) {
                    itemView.suitable_item_of_team2.visibility = View.VISIBLE
                    Glide.with(itemView.suitable_item_of_team2.context)
                        .load(champ.itemSuitable[1].itemAvatar)
                        .into(itemView.suitable_item_of_team2)
                }
                if (champ.itemSuitable.size == 3) {
                    itemView.suitable_item_of_team3.visibility = View.VISIBLE
                    Glide.with(itemView.suitable_item_of_team3.context)
                        .load(champ.itemSuitable[2].itemAvatar)
                        .into(itemView.suitable_item_of_team3)
                }
            }
            itemView.setOnClickListener { onItemClickListener.onClickListener(champ.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_show_by_origin_class, parent, false)
        )

    override fun getItemCount(): Int = champs.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(champs[position], onItemClickListener)

    fun addData(list: List<Champ>) {
        champs.clear()
        champs.addAll(list)
        notifyDataSetChanged()
    }

    data class Champ(
        val id: String,
        val name: String,
        val imgUrl: String,
        val cost: String,
        val threeStar:String,
        val itemSuitable: List<Item>
    ) {
        data class Item(
            val id: String,
            val itemName: String,
            val itemAvatar: String
        )
    }
}

