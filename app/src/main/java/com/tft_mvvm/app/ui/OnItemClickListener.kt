package com.tft_mvvm.app.ui

import com.example.presentation.features.champs.model.model.Champ

interface OnItemClickListener {
    fun onClickListener(champ: Champ?)
}