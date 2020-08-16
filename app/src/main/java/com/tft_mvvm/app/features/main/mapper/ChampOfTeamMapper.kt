package com.tft_mvvm.app.features.main.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.details.model.HeaderViewHolderModel
import com.tft_mvvm.app.features.main.adapter.AdapterShowChampInTeamBuilder
import com.tft_mvvm.domain.features.model.ChampListEntity

class ChampOfTeamMapper(private val itemOfTeamMapper: ItemOfTeamMapper) :
    Mapper<ChampListEntity.Champ, AdapterShowChampInTeamBuilder.Champ>() {
    override fun map(input: ChampListEntity.Champ): AdapterShowChampInTeamBuilder.Champ {
        return AdapterShowChampInTeamBuilder.Champ(
            name = input.name,
            cost = input.cost,
            id = input.id,
            imgUrl = input.linkImg,
            itemSuitable = itemOfTeamMapper.mapList(input.suitableItem)
        )
    }
}
