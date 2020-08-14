package com.tft_mvvm.app.features.main.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.base.OnItemClickListener
import com.tft_mvvm.app.features.details.model.ItemRv
import com.tft_mvvm.app.model.Champ
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.item_show_by_origin_class.view.*
import kotlinx.android.synthetic.main.section_header.view.*

class AdapterShowChampByRank(
    private val champs: ArrayList<ItemRv>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_TYPE: Int = 1
    val HEADER_TYPE: Int = 2

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
        return if (champs[position] is Champ) {
            ITEM_TYPE
        } else {
            HEADER_TYPE
        }
    }

    override fun getItemCount(): Int = champs.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        if (itemViewType == ITEM_TYPE) {
            (holder as ItemViewHolder).bind(champs[position] as Champ, onItemClickListener)
        } else {
            (holder as SectionHeaderViewHolder).bind(champs[position] as SectionHeaderViewHolder.SectionModel)
        }
    }

    class SectionHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(header: SectionModel) {
            val prefix = "Bậc "
            itemView.tvTitle.text = prefix + header.title
            when (header.title) {
                "S" -> itemView.tvTitle.setTextColor(Color.YELLOW)
                "A" -> itemView.tvTitle.setTextColor(Color.RED)
                "B" -> itemView.tvTitle.setTextColor(Color.BLUE)
                "C" -> itemView.tvTitle.setTextColor(Color.GREEN)
                "D" -> itemView.tvTitle.setTextColor(Color.GRAY)
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

    fun addData(list: List<ItemRv>) {
        champs.clear()
        val data = ArrayList<ItemRv>()
        data.addAll(list)
        for (i in 0 until data.size) {
            if (i == 0) {
                data.add(0, SectionHeaderViewHolder.SectionModel(((i + 1) as Champ).rank))
            } else if (i != 0 && SectionHeaderViewHolder.SectionModel((i as Champ).rank) != SectionHeaderViewHolder.SectionModel(
                    ((i + 1) as Champ).rank
                )
            ) {
                data.add(i + 1, SectionHeaderViewHolder.SectionModel(((i + 1) as Champ).rank))
            }
        }
        champs.addAll(list)
        notifyDataSetChanged()
    }

}