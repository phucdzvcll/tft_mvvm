package com.tft_mvvm.app.features.details.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.features.details.model.HeaderViewHolderModel
import com.tft_mvvm.app.features.details.model.ItemHolderViewHolder
import com.tft_mvvm.app.features.details.model.ItemRv
import com.tft_mvvm.app.base.OnItemClickListener
import com.tft_mvvm.app.features.details.model.TeamRecommendForChamp
import com.tft_mvvm.app.features.main.adapter.AdapterShowChampInTeamBuilder
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.iteam_team_recommend.view.*
import kotlinx.android.synthetic.main.item_header_rv_details_champ.view.*
import kotlinx.android.synthetic.main.item_rv_details_champ.view.*

class AdapterShowDetailsChamp(
    private val listItemRv: ArrayList<ItemRv>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val HEADER_TYPE: Int = 1
    val ITEM_TYPE: Int = 2
    val TEAM_TYPE: Int = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == HEADER_TYPE) {
            return HeaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_header_rv_details_champ, parent, false)
            )
        }
        if (viewType == ITEM_TYPE) {
            return ItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_rv_details_champ, parent, false)
            )
        } else {
            return TeamViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.iteam_team_recommend, parent, false)
            )
        }
    }

    override fun getItemCount() = listItemRv.size

    override fun getItemViewType(position: Int): Int {
        if (listItemRv[position] is HeaderViewHolderModel) {
            return HEADER_TYPE
        }
        if (listItemRv[position] is ItemHolderViewHolder) {
            return ITEM_TYPE
        } else {
            return TEAM_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemType = getItemViewType(position)
        if (itemType == HEADER_TYPE) {
            (holder as HeaderViewHolder).bind(
                listItemRv[position] as HeaderViewHolderModel,
                onItemClickListener
            )
        }
        if (itemType == ITEM_TYPE) {
            (holder as ItemViewHolder).bind(
                listItemRv[position] as ItemHolderViewHolder,
                onItemClickListener
            )
        }
        if (itemType == TEAM_TYPE) {
            (holder as TeamViewHolder).bind(
                listItemRv[position] as TeamRecommendForChamp,
                onItemClickListener
            )
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            headerViewHolderModel: HeaderViewHolderModel,
            onItemClickListener: OnItemClickListener
        ) {
            itemView.activated.text = headerViewHolderModel.activated
            itemView.skill_name.text = headerViewHolderModel.nameSkill
            Glide.with(itemView.skill_avatar.context)
                .load(headerViewHolderModel.linkAvatarSkill)
                .into(itemView.skill_avatar)
            Glide.with(itemView.champ_cover.context)
                .load(headerViewHolderModel.linkChampCover)
                .into(itemView.champ_cover)
            if (headerViewHolderModel.listSuitableItem.size == 3) {
                Glide.with(itemView.suitable_item_img_1.context)
                    .load(headerViewHolderModel.listSuitableItem[0].itemAvatar)
                    .into(itemView.suitable_item_img_1)
                Glide.with(itemView.suitable_item_img_2.context)
                    .load(headerViewHolderModel.listSuitableItem[1].itemAvatar)
                    .into(itemView.suitable_item_img_2)
                Glide.with(itemView.suitable_item_img_3.context)
                    .load(headerViewHolderModel.listSuitableItem[2].itemAvatar)
                    .into(itemView.suitable_item_img_3)
            }
            itemView.setOnClickListener { onItemClickListener.onClickListener("") }
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            itemDetailsViewHolderModel: ItemHolderViewHolder,
            onItemClickListener: OnItemClickListener
        ) {
            itemView.origin_or_class_name.text =
                itemDetailsViewHolderModel.classOrOrigin.classOrOriginName
            itemView.origin_or_class_content.text = itemDetailsViewHolderModel.classOrOrigin.content
            val adapterShowByOriginAndClass =
                AdapterShowByOriginAndClass(arrayListOf(), onItemClickListener)
            adapterShowByOriginAndClass.addData(itemDetailsViewHolderModel.listChamp)
            itemView.rv_origin_or_class.layoutManager = GridLayoutManager(itemView.context, 5)
            itemView.rv_origin_or_class.adapter = adapterShowByOriginAndClass
            val adapterShowBonusOfClassOrOrigin = AdapterShowBonusOfClassOrOrigin(
                arrayListOf()
            )
            adapterShowBonusOfClassOrOrigin.addData(itemDetailsViewHolderModel.classOrOrigin.bonus)
            itemView.rv_origin_or_class_bonus.layoutManager = LinearLayoutManager(itemView.context)
            itemView.rv_origin_or_class_bonus.adapter = adapterShowBonusOfClassOrOrigin
        }
    }

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            teamBuilder: TeamRecommendForChamp,
            onItemClickListener: OnItemClickListener
        ) {
            itemView.name_team.text = teamBuilder.name
            when (teamBuilder.name) {
                "D" -> itemView.name_team.setTextColor(Color.parseColor("#C5C1C1"))
                "C" -> itemView.name_team.setTextColor(Color.parseColor("#7FDF5F"))
                "B" -> itemView.name_team.setTextColor(Color.parseColor("#0099FF"))
                "A" -> itemView.name_team.setTextColor(Color.parseColor("#D152F4"))
                "S" -> itemView.name_team.setTextColor(Color.parseColor("#EFB135"))
            }
            itemView.rv_item_by_team_recommend?.layoutManager =
                GridLayoutManager(itemView.context, 5)
            val adapterShowByOriginAndClass = AdapterShowByOriginAndClass(
                arrayListOf(),
                onItemClickListener
            )
            adapterShowByOriginAndClass.addData(teamBuilder.listChamp)
            itemView.rv_item_by_team_recommend?.adapter = adapterShowByOriginAndClass
        }
    }

    fun addData(list: List<ItemRv>) {
        listItemRv.clear()
        listItemRv.addAll(list)
        notifyDataSetChanged()
    }

}
