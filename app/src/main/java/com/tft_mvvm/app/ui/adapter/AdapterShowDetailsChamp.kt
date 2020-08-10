package com.tft_mvvm.app.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.features.champ.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.item_header_rv_details_champ.view.*

class AdapterShowDetailsChamp(
    private val champ: Champ
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val HEADER_TYPE: Int = 1
    val ITEM_TYPE: Int = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    data class HeaderViewHolderModel(
        val id : String
    )

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(headerViewHolderModel: HeaderViewHolderModel) {
            itemView.activated.text=headerViewHolderModel.id
        }

    }
}