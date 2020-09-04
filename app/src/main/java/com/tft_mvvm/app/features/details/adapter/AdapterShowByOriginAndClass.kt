package com.tft_mvvm.app.features.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.base.OnItemClickListener
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.item_show_by_origin_class.view.*
import kotlinx.android.synthetic.main.item_show_champ_in_team.view.*

class AdapterShowByOriginAndClass(
    private val champs: ArrayList<AdapterShowDetailsChamp.Champ>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ChampOfTeamRecommendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(champ: AdapterShowDetailsChamp.Champ, onItemClickListener: OnItemClickListener) {
            Glide.with(itemView.imgShowChampByOriginClass.context)
                .load(champ.imgUrl)
                .into(itemView.imgShowChampByOriginClass)
            when (champ.cost) {
                "1" -> itemView.imgShowChampByOriginClass.setBackgroundResource(R.drawable.background_1_gold)
                "2" -> itemView.imgShowChampByOriginClass.setBackgroundResource(R.drawable.background_2_gold)
                "3" -> itemView.imgShowChampByOriginClass.setBackgroundResource(R.drawable.background_3_gold)
                "4" -> itemView.imgShowChampByOriginClass.setBackgroundResource(R.drawable.background_4_gold)
                "5" -> itemView.imgShowChampByOriginClass.setBackgroundResource(R.drawable.background_5_gold)
            }
            itemView.setOnClickListener { onItemClickListener.onClickListener(id = champ.id) }
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