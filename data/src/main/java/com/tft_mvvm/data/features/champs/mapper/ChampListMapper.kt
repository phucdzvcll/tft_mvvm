package com.tft_mvvm.data.features.champs.mapper

import com.example.common_jvm.mapper.Mapper

import com.tft_mvvm.data.features.champs.model.ChampListResponse
import com.tft_mvvm.data.local.model.ChampListDBO
import com.tft_mvvm.domain.features.champs.model.ChampListEntity

class ChampListMapper : Mapper<ChampListDBO.ChampDBO, ChampListEntity.Champ>() {
    override fun map(input: ChampListDBO.ChampDBO): ChampListEntity.Champ {
        return ChampListEntity.Champ(
            name = input.name,
            skillName = input.skillName,
            linkSkillAvatar = input.linkSkilAvatar,
            origin = input.origin,
            id = input.id,
            classs = input.classs,
            activated = input.activated,
            linkChampCover = input.linkChampCover,
            coat = input.coat,
            linkImg = input.linkImg
        )
    }

}