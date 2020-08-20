package com.tft_mvvm.data.mapper

import com.tft_mvvm.data.fake.ChampDBOFake
import com.tft_mvvm.data.fake.ChampEntityFake
import com.tft_mvvm.data.features.champs.mapper.ChampListMapper
import org.junit.Assert
import org.junit.Test

class ChampListMapperTest {

    private val champListMapper = ChampListMapper()

    @Test
    fun map(){
        val champDbo = ChampDBOFake.provideChampDbo()

        val expected = ChampEntityFake.provideChampEntity()

        val actual = champListMapper.map(champDbo)

        Assert.assertEquals(expected,actual)
    }
}