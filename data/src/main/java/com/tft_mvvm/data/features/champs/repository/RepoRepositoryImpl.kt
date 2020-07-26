package com.tft_mvvm.data.features.champs.repository

import com.tft_mvvm.data.features.champs.mapper.ChampListMapper
import com.tft_mvvm.data.features.champs.service.ApiService
import com.tft_mvvm.domain.features.champs.model.ChampListEntity

import com.tft_mvvm.domain.features.champs.repository.RepoRepository

class RepoRepositoryImpl(
    private val apiService: ApiService,
    private val champListMapper: ChampListMapper

) : RepoRepository {
    override suspend fun getChamps(
        name :String,
        linkImg:String,
        coat:String,
        origin:String,
        classs:String,
        id:String,
        nameSkill:String,
        linkSkillAvatar:String,
        activated:String
    ): ChampListEntity {
        val champListResponse = apiService.getChampList()
        return champListMapper.map(champListResponse)
    }


}