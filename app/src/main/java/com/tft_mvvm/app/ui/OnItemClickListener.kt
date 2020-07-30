package com.tft_mvvm.app.ui

import com.tft_mvvm.app.features.champ.model.Champ

interface OnItemClickListener {
    fun onClickListener(champ: Champ)
}