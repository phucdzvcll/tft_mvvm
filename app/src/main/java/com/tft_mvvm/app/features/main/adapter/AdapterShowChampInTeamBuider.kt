package com.tft_mvvm.app.features.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.item_show_by_origin_class.view.*

class AdapterShowChampInTeamBuilder(
    private val champs: ArrayList<AdapterShowRecommendTeamBuilder.ChampByRank>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AdapterShowChampInTeamBuilder.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(champ: AdapterShowRecommendTeamBuilder.ChampByRank, onItemClickListener: OnItemClickListener) {
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

    fun addData(list: List<AdapterShowRecommendTeamBuilder.ChampByRank>) {
        champs.clear()
        champs.addAll(list)
        notifyDataSetChanged()
    }
}