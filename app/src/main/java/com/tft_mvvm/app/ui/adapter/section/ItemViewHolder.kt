package com.tft_mvvm.app.ui.adapter.section

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.champ.R

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvItem: ImageView = itemView.findViewById(R.id.imgShowByOriginClass)
}