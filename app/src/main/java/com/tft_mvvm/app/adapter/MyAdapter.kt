package com.tft_mvvm.app.adapter
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.champ.R
import com.tft_mvvm.data.features.champs.model.Champ
import kotlinx.android.synthetic.main.item.view.*
class MyAdapter(private val champs: ArrayList<com.example.presentation.features.champs.model.model.Champ>, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<MyAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(champ: com.example.presentation.features.champs.model.model.Champ, onItemClickListener: OnItemClickListener) {
            itemView.name.text = champ.name
            Glide.with(itemView.img.context)
                .load(champ.linkImg)
                .into(itemView.img)
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




    fun addData(list: List<com.example.presentation.features.champs.model.model.Champ>) {
        champs.clear()
        champs.addAll(list)
        notifyDataSetChanged()
    }
    fun sortByName(){
            champs.sortBy { champ: com.example.presentation.features.champs.model.model.Champ ->champ.name  }
            notifyDataSetChanged()

    }
    fun sortByCoat(){
            champs.sortByDescending { champ: com.example.presentation.features.champs.model.model.Champ ->champ.coat  }
            notifyDataSetChanged()

    }
}