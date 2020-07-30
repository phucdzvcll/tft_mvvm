package com.tft_mvvm.app.ui.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.item.view.*
class MyAdapter(private val champs: ArrayList<Champ>, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<MyAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(champ: Champ, onItemClickListener: OnItemClickListener) {
            itemView.name.text = champ.name
            Glide.with(itemView.img.context)
                .load(champ.linkImg)
                .into(itemView.img)
//                .onLoadFailed({
//                    //
//                    //loadImage from disk /DATA/app/champ.id.png
//                })
            when(champ.coat){
                "1" -> itemView.img.setBackgroundResource(R.drawable.background_1_gold)
                "2" -> itemView.img.setBackgroundResource(R.drawable.background_2_gold)
                "3" -> itemView.img.setBackgroundResource(R.drawable.background_3_gold)
                "4" -> itemView.img.setBackgroundResource(R.drawable.background_4_gold)
                "5" -> itemView.img.setBackgroundResource(R.drawable.background_5_gold)
            }
            itemView.setOnClickListener { onItemClickListener.onClickListener(champ) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))

    override fun getItemCount(): Int = champs.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(champs[position], onItemClickListener)
    fun addData(list: List<Champ>) {
        champs.clear()
        champs.addAll(list)
        notifyDataSetChanged()
    }
}