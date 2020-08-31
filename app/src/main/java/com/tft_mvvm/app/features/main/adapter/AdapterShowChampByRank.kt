package com.tft_mvvm.app.features.main.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.base.OnItemClickListener
import com.tft_mvvm.app.model.Champ
import com.tft_mvvm.app.model.ItemRv
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.item_show_by_origin_class.view.*
import kotlinx.android.synthetic.main.section_header.view.*

class AdapterShowChampByRank(
    private val onItemClickListener: OnItemClickListener
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
        return if (listItem[position] is Champ) {
            ITEM_TYPE
        } else {
            HEADER_TYPE
        }
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        if (itemViewType == ITEM_TYPE) {
            (holder as ItemViewHolder).bind(listItem[position] as Champ, onItemClickListener)
        } else {
            (holder as SectionHeaderViewHolder).bind(listItem[position] as SectionHeaderViewHolder.SectionModel)
        }
    }

    class SectionHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(header: SectionModel) {
            itemView.tvTitle.text =header.title
            when (header.title) {
                "Bậc S" -> itemView.tvTitle.setTextColor(Color.YELLOW)
                "Bậc A" -> itemView.tvTitle.setTextColor(Color.RED)
                "Bậc B" -> itemView.tvTitle.setTextColor(Color.BLUE)
                "Bậc C" -> itemView.tvTitle.setTextColor(Color.GREEN)
            }
        }

        data class SectionModel(
            val title: String
        ) : ItemRv()

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
            itemView.setOnClickListener { onItemClickListener.onClickListener(champ.id) }
        }
    }

    fun setData(list: List<Champ>) {
        val listChampRankS = list.filter { champ -> champ.rank == "S" }
        val listChampRankA = list.filter { champ -> champ.rank == "A" }
        val listChampRankB = list.filter { champ -> champ.rank == "B" }
        val listChampRankC = list.filter { champ -> champ.rank == "C" }
        listItem.clear()
        if (listChampRankS.isNotEmpty()) {
            listItem.add(SectionHeaderViewHolder.SectionModel(title = "Bậc S"))
            listItem.addAll(listChampRankS)
        }
        if (listChampRankA.isNotEmpty()) {
            listItem.add(SectionHeaderViewHolder.SectionModel(title = "Bậc A"))
            listItem.addAll(listChampRankA)
        }
        if (listChampRankB.isNotEmpty()) {
            listItem.add(SectionHeaderViewHolder.SectionModel(title = "Bậc B"))
            listItem.addAll(listChampRankB)
        }
        if (listChampRankC.isNotEmpty()) {
            listItem.add(SectionHeaderViewHolder.SectionModel(title = "Bậc C"))
            listItem.addAll(listChampRankC)
        }
        notifyDataSetChanged()
    }

}