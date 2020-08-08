package com.tft_mvvm.app.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.domain.features.model.ChampListEntity
import java.util.ArrayList
import java.util.LinkedHashMap

class LoadChampByRankMapper(
    private val champListMapper: ChampMapper
) : Mapper<List<ChampListEntity.Champ>, Map<String, List<Champ>>>() {
    override fun map(input: List<ChampListEntity.Champ>): Map<String, List<Champ>> {
        val map = LinkedHashMap<String, List<Champ>>()
        for (rank in "SABCD") {
            val contactsList: MutableList<Champ> = ArrayList()
            for (champ in input) {
                if (champ.rankChamp[0] == rank) {
                    contactsList.add(champListMapper.map(champ))
                }
            }
            if (contactsList.isNotEmpty()) {
                map[rank.toString()] = contactsList
            }
        }
        return map
    }

}