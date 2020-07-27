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
    ): ChampListEntity {
        val champListResponse = apiService.getChampList()
        return champListMapper.map(champListResponse)
    }

    override suspend fun getChampsByOrigin(
        origin: String
    ): ChampListEntity {
        val champListResponse = apiService.getChampList()
        val champs = champListMapper.map(champListResponse).champs
        var resultByOrigin : MutableList<ChampListEntity.Champ> = mutableListOf()
        if(origin.isNotEmpty()){
            when(origin){
                "Không Tặc" -> resultByOrigin.addAll(champs.filter { champ -> champ.origin==origin||champ.name=="Gangplank" })
                else -> resultByOrigin.addAll(champs.filter { champ -> champ.origin==origin})
            }
        }
        return ChampListEntity(champs=resultByOrigin)
    }

    override suspend fun getChampsByClass(
        classs: String
    ): ChampListEntity {
        val champListResponse = apiService.getChampList()
        val champs = champListMapper.map(champListResponse).champs

        var resultByClasss : MutableList<ChampListEntity.Champ> = mutableListOf()
        if(classs.isNotEmpty()){
            when(classs){
                "Ma Tặc" -> resultByClasss.addAll(champs.filter { champ -> champ.classs==classs||champ.name=="Irelia" })
                else -> resultByClasss.addAll(champs.filter { champ -> champ.classs==classs})
            }
        }
        return ChampListEntity(champs=resultByClasss)
    }


}