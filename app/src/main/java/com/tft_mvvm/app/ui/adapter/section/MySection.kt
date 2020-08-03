package com.tft_mvvm.app.ui.adapter.section

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.champ.R
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters

class MySection(
    private val title: String,
    private val list: List<Champ>,
    private val onItemClickListener: OnItemClickListener
) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.item_show_by_origin_class)
        .headerResourceId(R.layout.section_ex1_header)
        .build()
) {

    override fun getContentItemsTotal(): Int {
        return list.size
    }

    override fun getItemViewHolder(view: View?): RecyclerView.ViewHolder? {
        return view?.let { ItemViewHolder(it) }
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as ItemViewHolder
        val champ = list[position]

        Glide.with(itemHolder.tvItem.context)
            .load(champ.linkImg)
            .into(itemHolder.tvItem)
        when (champ.cost) {
            "1" -> itemHolder.tvItem.setBackgroundResource(R.drawable.background_1_gold)
            "2" -> itemHolder.tvItem.setBackgroundResource(R.drawable.background_2_gold)
            "3" -> itemHolder.tvItem.setBackgroundResource(R.drawable.background_3_gold)
            "4" -> itemHolder.tvItem.setBackgroundResource(R.drawable.background_4_gold)
            "5" -> itemHolder.tvItem.setBackgroundResource(R.drawable.background_5_gold)
        }
        itemHolder.tvItem.setOnClickListener {
            onItemClickListener.onClickListener(champ)
        }
    }

    override fun getHeaderViewHolder(view: View?): RecyclerView.ViewHolder? {
        return view?.let { HeaderViewHolder(it) }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {
        val headerHolder = holder as HeaderViewHolder
        headerHolder.tvTitle.text = "Bậc $title"
        when (title) {
            "D" -> headerHolder.tvTitle.setTextColor(Color.parseColor("#C5C1C1"))
            "C" -> headerHolder.tvTitle.setTextColor(Color.parseColor("#7FDF5F"))
            "B" -> headerHolder.tvTitle.setTextColor(Color.parseColor("#0099FF"))
            "A" -> headerHolder.tvTitle.setTextColor(Color.parseColor("#D152F4"))
            "S" -> headerHolder.tvTitle.setTextColor(Color.parseColor("#EFB135"))
        }
    }

}
