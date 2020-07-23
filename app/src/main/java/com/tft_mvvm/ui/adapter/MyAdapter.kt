package com.tft_mvvm.ui.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.tft_mvm.R
import com.tft_mvvm.data.Champ
import kotlinx.android.synthetic.main.item.view.*
class MyAdapter(private val champs: ArrayList<com.tft_mvvm.data.Champ>) : RecyclerView.Adapter<MyAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(champ: com.tft_mvvm.data.Champ) {
            itemView.name.text = champ.name.toString()
            Glide.with(itemView.img.context)
                .load(champ.linkimg)
                .into(itemView.img)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))

    override fun getItemCount(): Int = champs.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(champs[position])

    fun addData(list: List<com.tft_mvvm.data.Champ>) {
        champs.addAll(list)
    }
}