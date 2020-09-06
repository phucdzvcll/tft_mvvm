package com.tft_mvvm.app.features.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.features.main.OnClickListenerPickChamp
import com.tft_mvvm.app.features.main.model.ClassAndOriginContent
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.item_show_by_origin_class.view.*
import kotlinx.android.synthetic.main.section_header.view.*

class AdapterShowChampByOriginAndClass(
    private val onClick: OnClickListenerPickChamp
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_TYPE: Int = 1
    val HEADER_TYPE: Int = 2
    private val listItem = mutableListOf<ItemRv>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADER_TYPE) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.section_header, parent, false)
            SectionHeaderViewHolder(
                view
            )
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_show_by_origin_class, parent, false)
            ItemViewHolder(
                view
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listItem[position] is ItemViewHolder.Champ) {
            ITEM_TYPE
        } else {
            HEADER_TYPE
        }
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        if (itemViewType == ITEM_TYPE) {
            (holder as ItemViewHolder).bind(
                listItem[position] as ItemViewHolder.Champ,
                onClick
            )
        } else {
            (holder as SectionHeaderViewHolder).bind(listItem[position] as SectionHeaderViewHolder.SectionModel)
        }
    }

    class SectionHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(header: SectionModel) {
            itemView.tvTitle.text = header.title
            Glide.with(itemView.img_class_origin_content.context)
                .load(header.imgUrl)
                .into(itemView.img_class_origin_content)
        }

        data class SectionModel(
            val title: String,
            val imgUrl: String
        ) : ItemRv()

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(champ: Champ, onItemClickListener: OnClickListenerPickChamp) {
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
            itemView.setOnClickListener { onItemClickListener.onClick(champMapper2(champ)) }


        }

        private fun champMapper2(champ: ItemViewHolder.Champ) = ClassAndOriginContent.Champ(
            id = champ.id,
            cost = champ.cost,
            classAndOriginName = champ.classAndOriginName,
            imgUrl = champ.imgUrl
        )

        data class Champ(
            val imgUrl: String,
            val cost: String,
            val id: String,
            val classAndOriginName: List<String>
        ) : ItemRv()
    }

    fun setData(list: List<ClassAndOriginContent>) {
        listItem.clear()
        list.forEach { classAndOriginContent ->
            listItem.add(
                SectionHeaderViewHolder.SectionModel(
                    classAndOriginContent.classOrOriginName,
                    classAndOriginContent.imgUrl
                )
            )
            val listChamp = classAndOriginContent.listChamp.sortedBy { champ -> champ.cost }
            listChamp.forEach { champ ->
                listItem.add(champMapper(champ))
            }
        }
        notifyDataSetChanged()
    }

    private fun champMapper(champ: ClassAndOriginContent.Champ) = ItemViewHolder.Champ(
        id = champ.id,
        cost = champ.cost,
        classAndOriginName = champ.classAndOriginName,
        imgUrl = champ.imgUrl
    )

    abstract class ItemRv {
    }
}