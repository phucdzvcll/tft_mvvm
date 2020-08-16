package com.tft_mvvm.app.model

import com.tft_mvvm.app.features.details.model.ItemRv

data class Champ(
    val id: String,
    val name: String,
    val imgUrl: String,
    val cost: String,
    val rank: String
):ItemRv()