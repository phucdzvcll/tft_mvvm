package com.tft_mvvm.app.features.dialog_show_details_champ.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.dialog_show_details_champ.model.ChampDialogModel
import com.tft_mvvm.domain.features.model.ChampListEntity

class ChampDialogModelMapper :
    Mapper<ChampListEntity.Champ, ChampDialogModel>() {
    override fun map(input: ChampListEntity.Champ): ChampDialogModel {
        return ChampDialogModel(
            name = input.name,
            cost = input.cost,
            linkChampCover = input.linkChampCover,
            activated = input.activated,
            skillName = input.skillName,
            linkSkillAvatar = input.linkSkillAvatar
        )
    }
}