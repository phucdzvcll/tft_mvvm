package com.tft_mvvm.app.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.item_show_by_gold.view.*
import kotlinx.android.synthetic.main.item_show_by_origin_class.view.*
import kotlinx.android.synthetic.main.section_header.view.*
import kotlin.collections.ArrayList

class AdapterShowChampByRank(
    private val champs: ArrayList<Champ>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_TYPE: Int = 1
    val HEADER_TYPE: Int = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADER_TYPE) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.section_header, parent, false)
            SectionHeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_show_by_origin_class, parent, false)
            ItemViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 || champs[position].rankChamp != champs[position - 1].rankChamp) {
            HEADER_TYPE
        } else {
            ITEM_TYPE
        }
    }

    override fun getItemCount(): Int = champs.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        if (itemViewType == ITEM_TYPE) {
            (holder as ItemViewHolder).bind(champs[position], onItemClickListener)
        } else {
            (holder as SectionHeaderViewHolder).bind(champs[position].rankChamp)
        }
    }

    class SectionHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(header: String) {
            val prefix = "Bậc "
            itemView.tvTitle.text = prefix + header
            when (header) {
                "S" -> itemView.tvTitle.setTextColor(Color.YELLOW)
                "A" -> itemView.tvTitle.setTextColor(Color.RED)
                "B" -> itemView.tvTitle.setTextColor(Color.BLUE)
                "C" -> itemView.tvTitle.setTextColor(Color.GREEN)
                "D" -> itemView.tvTitle.setTextColor(Color.GRAY)
            }
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(champ: Champ, onItemClickListener: OnItemClickListener) {
            Glide.with(itemView.imgShowByOriginClass.context)
                .load(champ.linkImg)
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
    
    fun addData(list: List<Champ>) {
        champs.clear()
        val data = ArrayList<Champ>()
        data.addAll(list)
        for (i in 0 until data.size) {
            if (i == 0) {
                data.add(0, data[i + 1])
            } else if (i != 0 && data[i].rankChamp != data[i + 1].rankChamp) {
                data.add(i + 1, data[i + 1])
            }
        }
        champs.addAll(data)
        notifyDataSetChanged()
    }
}