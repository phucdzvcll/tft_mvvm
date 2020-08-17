package com.tft_mvvm.app.features.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.model.Champ

import com.tft_mvvm.app.base.OnItemClickListener
import com.tft_mvvm.app.features.details.model.ItemRv
import com.tft_mvvm.app.features.details.model.TeamRecommendForChamp
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.item_show_by_origin_class.view.*

class AdapterShowByOriginAndClass(
    private val champs: ArrayList<ItemRv>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val CHAMP_TYPE = 1
    val CHAMP_FOR_TEAM_TYPE = 2

    class ChampViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
            itemView.setOnClickListener { onItemClickListener.onClickListener(id = champ.id) }
        }
    }

    class ChampOfTeamRecommendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(champ: TeamRecommendForChamp.Champ, onItemClickListener: OnItemClickListener) {
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
            if(champ.threeStar){
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
            itemView.setOnClickListener { onItemClickListener.onClickListener(id = champ.id) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (champs[position] is Champ) {
            return CHAMP_TYPE
        } else {
            return CHAMP_FOR_TEAM_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == CHAMP_TYPE) {
            return ChampViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_show_by_origin_class, parent, false)
            )
        } else {
            return ChampOfTeamRecommendViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_show_by_origin_class, parent, false)
            )
        }
    }


    override fun getItemCount(): Int = champs.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemType = getItemViewType(position)
        if (itemType == CHAMP_TYPE) {
            (holder as ChampViewHolder).bind(champs[position] as Champ, onItemClickListener)
        } else {
            (holder as ChampOfTeamRecommendViewHolder).bind(
                champs[position] as TeamRecommendForChamp.Champ,
                onItemClickListener
            )
        }
    }

    fun addData(list: List<ItemRv>) {
        champs.clear()
        champs.addAll(list)
        notifyDataSetChanged()
    }

}