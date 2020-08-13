package com.tft_mvvm.app.features.main.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.iteam_team_recommend.view.*
import kotlinx.android.synthetic.main.iteam_team_recommend.view.name_team as name_team1

class AdapterShowRecommendTeamBuilder(
    private val listTeamBuilder: ArrayList<TeamBuilder>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AdapterShowRecommendTeamBuilder.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var adapterShowByOriginAndClass: AdapterShowChampInTeamBuilder? = null
        fun bind(teamBuilder: TeamBuilder, onItemClickListener: OnItemClickListener) {
            itemView.name_team1.text = teamBuilder.name
            when (teamBuilder.name) {
                "D" -> itemView.name_team1.setTextColor(Color.parseColor("#C5C1C1"))
                "C" -> itemView.name_team1.setTextColor(Color.parseColor("#7FDF5F"))
                "B" -> itemView.name_team1.setTextColor(Color.parseColor("#0099FF"))
                "A" -> itemView.name_team1.setTextColor(Color.parseColor("#D152F4"))
                "S" -> itemView.name_team1.setTextColor(Color.parseColor("#EFB135"))
            }
            itemView.rv_item_by_team_recommend.layoutManager =
                GridLayoutManager(itemView.context, 5)
            adapterShowByOriginAndClass = AdapterShowChampInTeamBuilder(
                arrayListOf(),
                onItemClickListener
            )
            adapterShowByOriginAndClass?.addData(teamBuilder.listChamp)
            itemView.rv_item_by_team_recommend.adapter = adapterShowByOriginAndClass
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.iteam_team_recommend, parent, false)
        )

    override fun getItemCount() = listTeamBuilder.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTeamBuilder[position], onItemClickListener)
    }

    fun addData(list: List<TeamBuilder>) {
        listTeamBuilder.clear()
        listTeamBuilder.addAll(list)
        notifyDataSetChanged()
    }

    data class TeamBuilder(
        val name: String,
        val listChamp: List<ChampByRank>
    )

    data class ChampByRank(
        val id: String,
        val name: String,
        val imgUrl: String,
        val cost: String,
        val rank: String
    )
}


