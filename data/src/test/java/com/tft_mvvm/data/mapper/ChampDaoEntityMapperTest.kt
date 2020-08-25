package com.tft_mvvm.data.mapper

import com.tft_mvvm.data.features.champs.repository.fake.ChampDBOFake
import com.tft_mvvm.data.fake.ChampResponseFake
import com.tft_mvvm.data.features.champs.mapper.ChampDaoEntityMapper
import com.tft_mvvm.data.features.champs.remote.ChampListResponse
import com.tft_mvvm.data.features.champs.remote.Feed
import com.tft_mvvm.data.local.model.ChampListDBO
import org.junit.Assert
import org.junit.Test


class ChampDaoEntityMapperTest {

    private val champDaoEntityMapper = ChampDaoEntityMapper()

    @Test
    fun mapDefaultEmpty() {
        val listChampResponse = ChampResponseFake.provideChampResponseListNull(10)

        val expected = ChampListDBO(champDBOs = ChampDBOFake.provideChampDBOListEmpty(10))

        val champListResponse = ChampListResponse(
            feed = Feed(
                champs = listChampResponse
            )
        )

        val actual = champDaoEntityMapper.map(champListResponse)


        Assert.assertEquals(expected, actual)
    }

    @Test
    fun map() {
        val listChampResponse = ChampResponseFake.provideChampResponseList(10)

        val expected = ChampListDBO(champDBOs = ChampDBOFake.provideChampDBOList(10))

        val champListResponse = ChampListResponse(
            feed = Feed(
                champs = listChampResponse
            )
        )
        val actual = champDaoEntityMapper.map(champListResponse)
        Assert.assertEquals(expected, actual)
    }
}