package com.tft_mvvm.data.features.champs.repository

import com.tft_mvvm.data.exception_interceptor.RemoteExceptionInterceptor
import com.tft_mvvm.data.fake.ChampDBOFake
import com.tft_mvvm.data.fake.ChampEntityFake
import com.tft_mvvm.data.fake.ChampResponseFake
import com.tft_mvvm.data.features.champs.mapper.*
import com.tft_mvvm.data.features.champs.model.ChampListResponse
import com.tft_mvvm.data.features.champs.model.Feed
import com.tft_mvvm.data.features.champs.service.ApiService
import com.tft_mvvm.data.local.database.ChampDAO
import com.tft_mvvm.data.local.database.ClassAndOriginDAO
import com.tft_mvvm.data.local.database.ItemDAO
import com.tft_mvvm.data.local.database.TeamDAO
import com.tft_mvvm.data.local.model.ChampListDBO
import com.tft_mvvm.domain.features.model.ChampListEntity
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class RepoRepositoryImplTest {
    private val apiService: ApiService = mockk()
    private val champDAO: ChampDAO = mockk()
    private val teamDAO: TeamDAO = mockk()
    private val itemDAO: ItemDAO = mockk()
    private val itemDaoEntityMapper: ItemDaoEntityMapper = mockk()
    private val itemListMapper: ItemListMapper = mockk()
    private val classAndOriginDAO: ClassAndOriginDAO = mockk()
    private val teamListMapper: TeamListMapper = mockk()
    private val teamDaoEntityMapper: TeamDaoEntityMapper = mockk()
    private val remoteExceptionInterceptor: RemoteExceptionInterceptor = mockk()
    private val champListMapper: ChampListMapper = mockk()
    private val classAndOriginDaoEntityMapper: ClassAndOriginDaoEntityMapper = mockk()
    private val champDaoEntityMapper: ChampDaoEntityMapper = mockk()
    val repoRepository = RepoRepositoryImpl(
        apiService = apiService,
        champDAO = champDAO,
        teamDAO = teamDAO,
        itemDAO = itemDAO,
        itemDaoEntityMapper = itemDaoEntityMapper,
        itemListMapper = itemListMapper,
        classAndOriginDAO = classAndOriginDAO,
        teamListMapper = teamListMapper,
        teamDaoEntityMapper = teamDaoEntityMapper,
        remoteExceptionInterceptor = remoteExceptionInterceptor,
        champListMapper = champListMapper,
        classAndOriginDaoEntityMapper = classAndOriginDaoEntityMapper,
        champDaoEntityMapper = champDaoEntityMapper
    )

    @Test
    fun `getAllChamps champListMapper-mapList-success`() = runBlocking {
        //given - provide inputs
        val isForceLoadData = false
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        val listChampEntity = ChampEntityFake.provideChampEntityList(10)
        val expectedListChampEntity = ChampListEntity(champs = listChampEntity)
        coEvery { champDAO.getAllChamp() } answers { listChampDBO }
        every { champListMapper.mapList(listChampDBO) } answers { listChampEntity }

        //when - execute test method
        val either = repoRepository.getAllChamps(isForceLoadData = isForceLoadData)

        //then - verify
        either.either(
            failAction = {
                Assert.assertTrue(false)
            },
            successAction = { actualListChampEntity ->
                Assert.assertEquals(expectedListChampEntity, actualListChampEntity)
            }
        )
    }

    @Test
    fun `getAllChamps listChampEntity Empty success`() = runBlocking {
        val isForceLoadData = false
        val listChampResponse = ChampResponseFake.provideChampResponseList(10)
        val listChampDBO = ChampListDBO(ChampDBOFake.provideChampDBOList(10))
        val listChampEntity = ChampEntityFake.provideChampEntityList(10)
        val expectedChampListEntity = ChampListEntity(listChampEntity)
        coEvery { champDAO.getAllChamp() } answers { listOf() }
        every { champListMapper.mapList(listOf()) } answers { listOf() }
        coEvery { apiService.getChampList() } answers { ChampListResponse(Feed(listChampResponse)) }
        every { champDaoEntityMapper.map(ChampListResponse(Feed(listChampResponse))) } answers { listChampDBO }
        coEvery { champDAO.insertChamps(listChampDBO.champDBOs) } answers { Unit }
        coEvery { champDAO.getAllChamp() } answers { listChampDBO.champDBOs }
        every { champListMapper.mapList(listChampDBO.champDBOs) } answers { listChampEntity }
        val either = repoRepository.getAllChamps(isForceLoadData = isForceLoadData)

        either.either(
            failAction = {
                Assert.assertTrue(false)
            },
            successAction = { actualListChampEntity ->
                Assert.assertEquals(expectedChampListEntity, actualListChampEntity)
            }
        )

    }

}