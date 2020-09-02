package com.tft_mvvm.app.features.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.base.OnItemClickListener
import com.tft_mvvm.app.features.dialog_show_details_champ.model.ChampDialogModel
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.item_show_by_origin_class.view.*

class AdapterShowChampInTeam(
    private val champs: ArrayList<AdapterShowDetailsChamp.Champ>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ChampOfTeamRecommendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(champ: AdapterShowDetailsChamp.Champ, onItemClickListener: OnItemClickListener) {
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
            if (champ.threeStar=="3") {
                itemView.three_start.visibility = View.VISIBLE
            }
            val listItem = mutableListOf<ChampDialogModel.Item>()
            if (champ.itemSuitable.isNotEmpty()) {
                itemView.suitable_item_of_team1.visibility = View.VISIBLE
                Glide.with(itemView.suitable_item_of_team1.context)
                    .load(champ.itemSuitable[0].itemAvatar)
                    .into(itemView.suitable_item_of_team1)
                listItem.add(itemMapper(champ.itemSuitable[0]))
            }
            if (champ.itemSuitable.size >= 2) {
                itemView.suitable_item_of_team2.visibility = View.VISIBLE
                Glide.with(itemView.suitable_item_of_team2.context)
                    .load(champ.itemSuitable[1].itemAvatar)
                    .into(itemView.suitable_item_of_team2)
                listItem.add(itemMapper(champ.itemSuitable[1]))
            }
            if (champ.itemSuitable.size == 3) {
                itemView.suitable_item_of_team3.visibility = View.VISIBLE
                Glide.with(itemView.suitable_item_of_team3.context)
                    .load(champ.itemSuitable[2].itemAvatar)
                    .into(itemView.suitable_item_of_team3)
                listItem.add(itemMapper(champ.itemSuitable[2]))
            }
            itemView.setOnClickListener {
                onItemClickListener.onClickListenerForChampInTeam(
                    id = champ.id,
                    listItem = listItem,
                    star = champ.threeStar
                )
            }
        }

        private fun itemMapper(item: AdapterShowDetailsChamp.Item): ChampDialogModel.Item {
            return ChampDialogModel.Item(
                itemName = item.itemName,
                id = item.id,
                itemAvatar = item.itemAvatar
            )
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ChampOfTeamRecommendViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_show_by_origin_class, parent, false)
        )
    }


    override fun getItemCount(): Int = champs.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ChampOfTeamRecommendViewHolder).bind(champs[position], onItemClickListener)
    }

    fun addData(list: List<AdapterShowDetailsChamp.Champ>) {
        champs.clear()
        champs.addAll(list)
        notifyDataSetChanged()
    }

}