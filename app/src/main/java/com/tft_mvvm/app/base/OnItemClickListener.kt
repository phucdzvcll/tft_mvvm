package com.tft_mvvm.app.base

import com.tft_mvvm.app.features.dialog_show_details_champ.model.ChampDialogModel

interface OnItemClickListener {
    fun onClickListener(id: String)
    fun onClickListenerForChampInTeam(id: String, listItem: List<ChampDialogModel.Item>)
}