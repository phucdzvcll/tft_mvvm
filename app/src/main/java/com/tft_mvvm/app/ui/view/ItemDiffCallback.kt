package com.tft_mvvm.app.ui.view

import androidx.recyclerview.widget.DiffUtil
import com.tft_mvvm.domain.features.champs.model.ChampListEntity

class ItemDiffCallback(
    private val oldList: List<ChampListEntity.Champ>,
    private val newList: List<ChampListEntity.Champ>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].activated == newList[newItemPosition].activated
                && oldList[oldItemPosition].classs == newList[newItemPosition].classs
                && oldList[oldItemPosition].coat == newList[newItemPosition].coat
                && oldList[oldItemPosition].linkChampCover == newList[newItemPosition].linkChampCover
                && oldList[oldItemPosition].linkImg == newList[newItemPosition].linkImg
                && oldList[oldItemPosition].linkSkillAvatar == newList[newItemPosition].linkSkillAvatar
                && oldList[oldItemPosition].name == newList[newItemPosition].name
                && oldList[oldItemPosition].origin == newList[newItemPosition].origin
                && oldList[oldItemPosition].skillName == newList[newItemPosition].skillName
    }


}
