package com.tft_mvvm.data.features.champs.mapper

import com.example.common_jvm.extension.nullable.defaultEmpty
import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.data.features.champs.model.ChampListResponse
import com.tft_mvvm.data.local.model.ChampListDBO

class ChampDaoEntityMapper : Mapper<ChampListResponse?, ChampListDBO>() {
    override fun map(input: ChampListResponse?): ChampListDBO {
        val champDBO = input?.feed?.champs?.filterNotNull().defaultEmpty().map { champDBO ->
            ChampListDBO.ChampDBO(
                id = champDBO.id?.value.defaultEmpty(),
                cost = champDBO.cost?.value.defaultEmpty(),
                linkImg = champDBO.linkImg?.value.defaultEmpty(),
                linkChampCover = champDBO.linkChampCover?.value.defaultEmpty(),
                activated = champDBO.activated?.value.defaultEmpty(),
                classs = champDBO.classs?.value.defaultEmpty(),
                origin = champDBO.origin?.value.defaultEmpty(),
                skillName = champDBO.skillName?.value.defaultEmpty(),
                name = champDBO.name?.value.defaultEmpty(),
                rankChamp = champDBO.rankChamp?.value.defaultEmpty(),
                suitableItem = champDBO.suitableItem?.value.defaultEmpty(),
                linkSkillAvatar = champDBO.linkSkillAvatar?.value.defaultEmpty()
            )
        }
        return ChampListDBO(champDBOs = champDBO)
    }
}