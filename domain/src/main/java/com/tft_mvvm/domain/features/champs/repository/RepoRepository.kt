package com.tft_mvvm.domain.features.champs.repository
import com.tft_mvvm.domain.features.champs.model.ChampListEntity

interface RepoRepository {
    suspend fun getChamps(
        name : String,
        linkImg:String,
        coat : String,
        origin:String,
        classs:String,
        id:String,
        skillName:String,
        linkSkillAvatar:String,
        activated:String
    ): ChampListEntity


}