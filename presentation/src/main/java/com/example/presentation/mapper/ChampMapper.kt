package com.example.presentation.mapper

import com.example.common_jvm.mapper.Mapper
import com.example.presentation.features.champs.model.model.Champ
import com.tft_mvvm.domain.features.champs.model.ChampListEntity

class ChampMapper : Mapper<ChampListEntity.Champ, Champ>() {
    override fun map(input: ChampListEntity.Champ): Champ {
        return Champ(
            name = input.name,
            linkImg = input.linkImg,
            coat = input.coat,
            classs = input.classs,
            origin = input.origin,
            id = input.id,
            activated = input.activated,
            skillName = input.skillName,
            linkSkilAvatar = input.linkSkillAvatar,
            linkChampCover = input.linkChampCover
        )
    }
}