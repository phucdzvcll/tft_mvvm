package com.tft_mvvm.data.features.champs.repository.fake

import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.model.ClassAndOriginListEntity

object ClassAndOriginContentEntityFake {

    fun provideClassAndOriginContentEntity(index: Int = 1) =
        ClassAndOriginListEntity.ClassAndOrigin(
            classOrOriginName = "name$index",
            content = "content$index",
            bonus = "bonus$index",
            champEntity = ChampListEntity(
                ChampEntityFake.provideChampEntityList(
                    10
                )
            )
        )
}