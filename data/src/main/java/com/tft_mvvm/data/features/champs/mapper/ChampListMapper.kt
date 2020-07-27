package com.tft_mvvm.data.features.champs.mapper

import com.example.common_jvm.mapper.Mapper
import com.example.common_jvm.nullable.defaultEmpty
import com.tft_mvvm.data.features.champs.model.ChampListResponse
import com.tft_mvvm.domain.features.champs.model.ChampListEntity

class ChampListMapper : Mapper<ChampListResponse?, ChampListEntity>() {
    override fun map(input: ChampListResponse?): ChampListEntity {
        val champ = input?.feed?.champs?.filterNotNull().defaultEmpty().map { champ ->
            ChampListEntity.Champ(
                name = champ.name.value,
                linkImg = champ.linkImg.value,
                coat = champ.coat.value,
                origin = champ.origin.value,
                classs = champ.classs.value,
                id = champ.id.value,
                skillName = champ.skillName.value,
                linkSkillAvatar = champ.linkSkillAvatar.value,
                activated = champ.activated.value,
                linkChampCover = champ.linkChampCover.value
            )
        }
        return ChampListEntity(champs = champ)
    }
}