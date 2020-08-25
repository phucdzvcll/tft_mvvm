package com.tft_mvvm.data.mapper

import com.tft_mvvm.data.features.champs.repository.fake.ClassAndOriginDBOFake
import com.tft_mvvm.data.fake.ClassAndOriginListResponseFake
import com.tft_mvvm.data.features.champs.mapper.ClassAndOriginDaoEntityMapper
import com.tft_mvvm.data.features.champs.remote.ClassAndOriginListResponse
import com.tft_mvvm.data.features.champs.remote.FeedClassAndOrigin
import com.tft_mvvm.data.local.model.ClassAndOriginListDBO
import org.junit.Assert
import org.junit.Test

class ClassAndOriginDaoEntityMapperTest {
    private val classAndOriginDaoEntityMapper = ClassAndOriginDaoEntityMapper()

    @Test
    fun mapDefaultEmpty(){
        val classAndOriginResponse = ClassAndOriginListResponse(feedClassAndOrigin = FeedClassAndOrigin(
            classAndOrigins = ClassAndOriginListResponseFake.provideListClassAndOriginEmpty(10)
        ))

        val expected = ClassAndOriginListDBO(classAndOrigins = ClassAndOriginDBOFake.provideListClassAndOriginDBOEmpty(10))

        val actual = classAndOriginDaoEntityMapper.map(classAndOriginResponse)

        Assert.assertEquals(expected,actual)
    }
}