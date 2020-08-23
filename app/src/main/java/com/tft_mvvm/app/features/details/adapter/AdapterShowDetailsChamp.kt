package com.tft_mvvm.app.features.details.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.base.OnItemClickListener
import com.tft_mvvm.app.features.details.model.ChampDetailsModel
import com.tft_mvvm.app.model.ItemRv
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.header_details_champ.view.*
import kotlinx.android.synthetic.main.iteam_team_recommend.view.*
import kotlinx.android.synthetic.main.item_header_rv_details_champ.view.*
import kotlinx.android.synthetic.main.item_rv_details_champ.view.*

class AdapterShowDetailsChamp(
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listItemRv = mutableListOf<ItemRv>()
    val HEADER_TYPE: Int = 1
    val ITEM_TYPE: Int = 2
    val TEAM_TYPE: Int = 3
    val SECTION_TYPE: Int = 4

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
        }
        if (viewType == TEAM_TYPE) {
            return TeamViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.iteam_team_recommend, parent, false)
            )
        } else {
            return SectionViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_details_champ, parent, false)
            )
        }
    }

    override fun getItemCount() = listItemRv.size

    override fun getItemViewType(position: Int): Int {
        if (listItemRv[position] is HeaderViewHolder.HeaderModel) {
            return HEADER_TYPE
        }
        if (listItemRv[position] is ItemViewHolder.ClassAndOriginContent) {
            return ITEM_TYPE
        }
        if (listItemRv[position] is TeamViewHolder.TeamRecommend) {
            return TEAM_TYPE
        } else {
            return SECTION_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemType = getItemViewType(position)
        if (itemType == HEADER_TYPE) {
            (holder as HeaderViewHolder).bind(
                listItemRv[position] as HeaderViewHolder.HeaderModel
            )
        }
        if (itemType == ITEM_TYPE) {
            (holder as ItemViewHolder).bind(
                listItemRv[position] as ItemViewHolder.ClassAndOriginContent,
                onItemClickListener
            )
        }
        if (itemType == TEAM_TYPE) {
            (holder as TeamViewHolder).bind(
                listItemRv[position] as TeamViewHolder.TeamRecommend,
                onItemClickListener
            )
        }
        if (itemType == SECTION_TYPE) {
            (holder as SectionViewHolder).bind(listItemRv[position] as SectionViewHolder.SectionModel)
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            headerModel: HeaderModel
        ) {
            itemView.activated.text = headerModel.activated
            itemView.skill_name.text = headerModel.nameSkill
            Glide.with(itemView.skill_avatar.context)
                .load(headerModel.linkAvatarSkill)
                .into(itemView.skill_avatar)
            Glide.with(itemView.champ_cover.context)
                .load(headerModel.linkChampCover)
                .into(itemView.champ_cover)
            if (headerModel.listSuitableItem.size == 3) {
                Glide.with(itemView.suitable_item_img_1.context)
                    .load(headerModel.listSuitableItem[0].itemAvatar)
                    .into(itemView.suitable_item_img_1)
                itemView.suitable_item_img_1.setOnClickListener {
                    Toast.makeText(
                        itemView.suitable_item_img_1.context,
                        headerModel.listSuitableItem[0].itemName,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Glide.with(itemView.suitable_item_img_2.context)
                    .load(headerModel.listSuitableItem[1].itemAvatar)
                    .into(itemView.suitable_item_img_2)
                itemView.suitable_item_img_2.setOnClickListener {
                    Toast.makeText(
                        itemView.suitable_item_img_2.context,
                        headerModel.listSuitableItem[1].itemName,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Glide.with(itemView.suitable_item_img_3.context)
                    .load(headerModel.listSuitableItem[2].itemAvatar)
                    .into(itemView.suitable_item_img_3)
                itemView.suitable_item_img_3.setOnClickListener {
                    Toast.makeText(
                        itemView.suitable_item_img_3.context,
                        headerModel.listSuitableItem[2].itemName,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        data class HeaderModel(
            val nameSkill: String,
            val activated: String,
            val linkAvatarSkill: String,
            val linkChampCover: String,
            val name: String,
            val cost: String,
            val listSuitableItem: List<Item>
        ) : ItemRv()

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            classAndOriginContent: ClassAndOriginContent,
            onItemClickListener: OnItemClickListener
        ) {
            itemView.origin_or_class_name.text =
                classAndOriginContent.classOrOriginName
            itemView.origin_or_class_content.text = classAndOriginContent.content
            val adapterShowByOriginAndClass =
                AdapterShowByOriginAndClass(arrayListOf(), onItemClickListener)
            adapterShowByOriginAndClass.addData(classAndOriginContent.listChamp)
            itemView.rv_origin_or_class.layoutManager = GridLayoutManager(itemView.context, 5)
            itemView.rv_origin_or_class.adapter = adapterShowByOriginAndClass
            val size = classAndOriginContent.bonus.size
            if (size > 0) {
                val itemBonus = classAndOriginContent.bonus[0].split(":")
                itemView.item_bonus_count_1.text = itemBonus[0]
                itemView.bonus_content_1.text = itemBonus[1]
                itemView.item_bonus_1.visibility = View.VISIBLE
            }
            if (size > 1) {
                val itemBonus = classAndOriginContent.bonus[1].split(":")
                itemView.item_bonus_count_2.text = itemBonus[0]
                itemView.bonus_content_2.text = itemBonus[1]
                itemView.item_bonus_2.visibility = View.VISIBLE
            }
            if (size > 2) {
                val itemBonus = classAndOriginContent.bonus[2].split(":")
                itemView.item_bonus_count_3.text = itemBonus[0]
                itemView.bonus_content_3.text = itemBonus[1]
                itemView.item_bonus_3.visibility = View.VISIBLE
            }
            if (size > 3) {
                val itemBonus = classAndOriginContent.bonus[3].split(":")
                itemView.item_bonus_count_4.text = itemBonus[0]
                itemView.bonus_content_4.text = itemBonus[1]
                itemView.item_bonus_4.visibility = View.VISIBLE
            }
        }

        data class ClassAndOriginContent(
            val listChamp: List<Champ>,
            val classOrOriginName: String,
            val bonus: List<String>,
            val content: String
        ) : ItemRv()

    }

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            teamBuilder: TeamRecommend,
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

        data class TeamRecommend(
            val name: String,
            val listChamp: List<Champ>
        ) : ItemRv()

    }

    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(sectionModel: SectionModel) {
            itemView.tv_header_details.text = sectionModel.section
        }

        data class SectionModel(val section: String) : ItemRv()

    }

    data class Champ(
        val id: String,
        val name: String,
        val imgUrl: String,
        val cost: String,
        val threeStar: String,
        val itemSuitable: List<Item>
    )

    data class Item(
        val id: String,
        val itemName: String,
        val itemAvatar: String
    )

    fun addData(champDetails: ChampDetailsModel) {
        listItemRv.clear()
        if (champDetails.headerModel != null) {
            listItemRv.add(
                HeaderViewHolder.HeaderModel(
                    nameSkill = champDetails.headerModel.nameSkill,
                    listSuitableItem = itemMapper(champDetails.headerModel.listSuitableItem),
                    linkAvatarSkill = champDetails.headerModel.linkAvatarSkill,
                    linkChampCover = champDetails.headerModel.linkChampCover,
                    activated = champDetails.headerModel.activated,
                    cost = champDetails.headerModel.cost,
                    name = champDetails.headerModel.name
                )
            )
        }

        if (champDetails.listItem.isNotEmpty()) {
            listItemRv.add(SectionViewHolder.SectionModel("Tộc và Hệ"))
            champDetails.listItem.forEach { item ->
                listItemRv.add(classAndOriginContentMapper(item))
            }
        }
        if (champDetails.listTeamRecommend.isNotEmpty()) {
            listItemRv.add(SectionViewHolder.SectionModel("Đội Hình Thích Hợp"))
            champDetails.listTeamRecommend.forEach { team ->
                listItemRv.add(teamRecommendMapper(team))
            }
        }
        notifyDataSetChanged()
    }

    private fun itemMapper(listItem: List<ChampDetailsModel.Item>): List<Item> {
        val listItemDetails = mutableListOf<Item>()
        listItem.forEach { item ->
            listItemDetails.add(
                Item(
                    itemName = item.itemName,
                    itemAvatar = item.itemAvatar,
                    id = item.id
                )
            )
        }
        return listItemDetails.toList()
    }

    private fun classAndOriginContentMapper(classAndOriginContent: ChampDetailsModel.ClassAndOriginContent): ItemViewHolder.ClassAndOriginContent {
        return ItemViewHolder.ClassAndOriginContent(
            classOrOriginName = classAndOriginContent.classOrOriginName,
            bonus = classAndOriginContent.bonus,
            content = classAndOriginContent.content,
            listChamp = champAdapterDetailsMapper(classAndOriginContent.listChamp)
        )
    }

    private fun champAdapterDetailsMapper(listChamp: List<ChampDetailsModel.Champ>): List<Champ> {
        val listChampDetailsModel = mutableListOf<Champ>()
        listChamp.forEach { champ ->
            listChampDetailsModel.add(
                Champ(
                    id = champ.id,
                    name = champ.name,
                    cost = champ.cost,
                    threeStar = champ.threeStar,
                    itemSuitable = itemMapper(champ.itemSuitable),
                    imgUrl = champ.imgUrl
                )
            )
        }
        return listChampDetailsModel
    }

    private fun teamRecommendMapper(teamRecommend: ChampDetailsModel.TeamRecommend): TeamViewHolder.TeamRecommend {
        return TeamViewHolder.TeamRecommend(
            name = teamRecommend.name,
            listChamp = champAdapterDetailsMapper(teamRecommend.listChamp)
        )
    }
}