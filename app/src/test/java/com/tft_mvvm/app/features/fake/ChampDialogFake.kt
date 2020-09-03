package com.tft_mvvm.app.features.fake

import com.tft_mvvm.app.features.dialog_show_details_champ.model.ChampDialogModel
import com.tft_mvvm.domain.features.model.ChampListEntity

object ChampDialogFake {

    fun provideChampDialog(id: String = "1") = ChampDialogModel(
        name = "name$id",
        cost = "1$id",
        linkChampCover = "http://linkchamp$id.google/",
        itemSuitable = listOf(),
        skillName = "skill $id",
        activated = "activated $id",
        linkSkillAvatar = "http://linkskill$id.google/",
        star = "1"
    )
}