package com.tft_mvvm.data.features.champs.repository

import com.tft_mvvm.data.exception_interceptor.RemoteExceptionInterceptor
import com.tft_mvvm.data.fake.*
import com.tft_mvvm.data.features.champs.mapper.*
import com.tft_mvvm.data.features.champs.model.*
import com.tft_mvvm.data.features.champs.service.ApiService
import com.tft_mvvm.data.local.database.ChampDAO
import com.tft_mvvm.data.local.database.ClassAndOriginDAO
import com.tft_mvvm.data.local.database.ItemDAO
import com.tft_mvvm.data.local.database.TeamDAO
import com.tft_mvvm.data.local.model.ChampListDBO
import com.tft_mvvm.data.local.model.ClassAndOriginListDBO
import com.tft_mvvm.data.local.model.ItemListDBO
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.model.ClassAndOriginListEntity
import io.mockk.*
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
    fun `getAllChamp isForceLoadData-true reloadChampDataFromNetwork`() = runBlocking {
        //given
        val isForceLoadData = true
        val listChampResponse = ChampResponseFake.provideChampResponseList(10)
        val champListResponse = ChampListResponse(Feed(champs = listChampResponse))
        val champListDBO = ChampListDBO(champDBOs = ChampDBOFake.provideChampDBOList(10))
        val champListEntity = ChampEntityFake.provideChampEntityList(10)
        coEvery { apiService.getChampList() } returns champListResponse
        every { champDaoEntityMapper.map(champListResponse) } returns champListDBO
        coEvery { champDAO.deleteAllChampTable() } returns Unit
        coEvery { champDAO.insertChamps(champListDBO.champDBOs) } returns Unit
        coEvery { champDAO.getAllChamp() } returns champListDBO.champDBOs
        every { champListMapper.mapList(champListDBO.champDBOs) } returns champListEntity

        //when
        repoRepository.getAllChamps(isForceLoadData)

        //then
        verify(exactly = 1) { champDaoEntityMapper.map(champListResponse) }
        coVerify(exactly = 1) { champDAO.deleteAllChampTable() }
        coVerify(exactly = 1) { champDAO.insertChamps(champListDBO.champDBOs) }
    }

    @Test
    fun `getAllChamp isForceLoadData-true success`() = runBlocking {
        val isForceLoadData = true
        val champListDBO = ChampListDBO(champDBOs = ChampDBOFake.provideChampDBOList(10))
        val champListEntity = ChampEntityFake.provideChampEntityList(10)
        val listChampResponse = ChampResponseFake.provideChampResponseList(10)
        val champListResponse = ChampListResponse(Feed(champs = listChampResponse))
        val expected = ChampListEntity(champs = champListEntity)
        coEvery { apiService.getChampList() } returns champListResponse
        every { champDaoEntityMapper.map(champListResponse) } returns champListDBO
        coEvery { champDAO.deleteAllChampTable() } returns Unit
        coEvery { champDAO.insertChamps(champListDBO.champDBOs) } returns Unit
        coEvery { champDAO.getAllChamp() } returns champListDBO.champDBOs
        every { champListMapper.mapList(champListDBO.champDBOs) } returns champListEntity

        val either = repoRepository.getAllChamps(isForceLoadData)

        coVerify(exactly = 1) { champListMapper.mapList(champListDBO.champDBOs) }
        either.either(
            failAction = { Assert.assertTrue(false) },
            successAction = { actual -> Assert.assertEquals(expected, actual) }
        )
    }

    @Test
    fun `getAllChamp isForceLoadData-false localChamps-empty`() = runBlocking {
        //given
        val isForceLoadData = false
        val listChampResponse = ChampResponseFake.provideChampResponseList(10)
        val champListResponse = ChampListResponse(Feed(champs = listChampResponse))
        val champListDBO = ChampListDBO(champDBOs = ChampDBOFake.provideChampDBOList(10))
        val champListEntity = ChampEntityFake.provideChampEntityList(10)

        coEvery { apiService.getChampList() } returns champListResponse
        every { champDaoEntityMapper.map(champListResponse) } returns champListDBO
        coEvery { champDAO.deleteAllChampTable() } returns Unit
        coEvery { champDAO.insertChamps(champListDBO.champDBOs) } returns Unit
        coEvery { champDAO.getAllChamp() } returnsMany listOf(listOf(), champListDBO.champDBOs)
        every { champListMapper.mapList(champListDBO.champDBOs) } returns champListEntity

        //when
        repoRepository.getAllChamps(isForceLoadData)

        //then
        verify(exactly = 1) { champDaoEntityMapper.map(champListResponse) }
        coVerify(exactly = 1) { champDAO.deleteAllChampTable() }
        coVerify(exactly = 1) { champDAO.insertChamps(champListDBO.champDBOs) }
    }

    @Test
    fun `getAllChamp isForceLoadData-false localChamps-not-empty`() = runBlocking {
        //given
        val isForceLoadData = false
        val listChampResponse = ChampResponseFake.provideChampResponseList(10)
        val champListResponse = ChampListResponse(Feed(champs = listChampResponse))
        val champListDBO = ChampListDBO(champDBOs = ChampDBOFake.provideChampDBOList(10))
        val champListEntity = ChampEntityFake.provideChampEntityList(10)

        coEvery { apiService.getChampList() } returns champListResponse
        every { champDaoEntityMapper.map(champListResponse) } returns champListDBO
        coEvery { champDAO.deleteAllChampTable() } returns Unit
        coEvery { champDAO.insertChamps(champListDBO.champDBOs) } returns Unit
        coEvery { champDAO.getAllChamp() } returns champListDBO.champDBOs
        every { champListMapper.mapList(champListDBO.champDBOs) } returns champListEntity

        //when
        repoRepository.getAllChamps(isForceLoadData)

        //then
        verify(exactly = 0) { champDaoEntityMapper.map(champListResponse) }
        coVerify(exactly = 0) { champDAO.deleteAllChampTable() }
        coVerify(exactly = 0) { champDAO.insertChamps(champListDBO.champDBOs) }
    }

    @Test
    fun `getAllChamp isForceLoadData-false success`() = runBlocking {
        //given
        val isForceLoadData = false
        val listChampResponse = ChampResponseFake.provideChampResponseList(10)
        val champListResponse = ChampListResponse(Feed(champs = listChampResponse))
        val champListDBO = ChampListDBO(champDBOs = ChampDBOFake.provideChampDBOList(10))
        val champListEntity = ChampEntityFake.provideChampEntityList(10)
        val expected = ChampListEntity(champs = champListEntity)
        coEvery { apiService.getChampList() } returns champListResponse
        every { champDaoEntityMapper.map(champListResponse) } returns champListDBO
        coEvery { champDAO.deleteAllChampTable() } returns Unit
        coEvery { champDAO.insertChamps(champListDBO.champDBOs) } returns Unit
        coEvery { champDAO.getAllChamp() } returnsMany listOf(listOf(), champListDBO.champDBOs)
        every { champListMapper.mapList(champListDBO.champDBOs) } returns champListEntity

        //when
        val allChamps = repoRepository.getAllChamps(isForceLoadData)

        //then
        allChamps.either(
            failAction = { Assert.assertTrue(false) },
            successAction = { actual -> Assert.assertEquals(expected, actual) }
        )
    }

    @Test
    fun `getAllChamp getAllChamp-apiService-error`() = runBlocking {
        //given
        val isForceLoadData = true
        val exception = Exception()
        val expected = CommonFake.provideFailure(exception)
        coEvery { apiService.getChampList() } throws exception
        every { remoteExceptionInterceptor.handleException(exception) } returns expected

        //when
        val allChamps = repoRepository.getAllChamps(isForceLoadData)

        //then
        allChamps.either(
            failAction = { actual -> Assert.assertEquals(expected, actual) },
            successAction = { Assert.assertTrue(false) }
        )
    }

    @Test
    fun `getAllChamp getAllChamp-champDAO-getAllChamp-error`() = runBlocking {
        //given
        val isForceLoadData = false
        val exception = Exception()
        val expected = CommonFake.provideFailure(exception)
        coEvery { champDAO.getAllChamp() } throws exception
        every { remoteExceptionInterceptor.handleException(exception) } returns expected

        //when
        val allChamps = repoRepository.getAllChamps(isForceLoadData)

        //then
        allChamps.either(
            failAction = { actual -> Assert.assertEquals(expected, actual) },
            successAction = { Assert.assertTrue(false) }
        )
    }

    @Test
    fun `getChampById itemDAO-getAllItem-empty success`() = runBlocking {
        val id = "11"
        val listItemResponse = ItemResponseFake.provideListItemResponse(10)
        val listItemDBO = ItemDBOFake.provideListItemResponse(10)
        val champDBO = ChampDBOFake.provideChampDbo(1)
        val itemListResponse = ItemListResponse(FeedItem(items = listItemResponse))
        val expected = ChampListEntity.Champ(
            id = champDBO.id,
            cost = champDBO.cost,
            linkImg = champDBO.linkImg,
            activated = champDBO.activated,
            linkChampCover = champDBO.linkChampCover,
            originAndClassName = champDBO.originAndClassName.split(","),
            skillName = champDBO.skillName,
            linkSkillAvatar = champDBO.linkSkillAvatar,
            rankChamp = champDBO.rankChamp,
            suitableItem = mutableListOf(
                ItemEntityFake.provideItemEntity(1),
                ItemEntityFake.provideItemEntity(1)
            ),
            threeStar = false,
            name = champDBO.name
        )
        coEvery { itemDAO.getAllItem() } returnsMany listOf(listOf(), listItemDBO)
        coEvery { apiService.getItemListResponse() } returns itemListResponse
        every { itemDaoEntityMapper.map(itemListResponse) } returns ItemListDBO(items = listItemDBO)
        coEvery { itemDAO.insertItems(listItemDBO) } returns Unit
        coEvery { champDAO.getChampById(id) } returns champDBO
        coEvery { itemDAO.getItemByListId(champDBO.suitableItem.split(",")) } returns mutableListOf(
            ItemDBOFake.provideItemDBO(1),
            ItemDBOFake.provideItemDBO(1)
        )
        every {
            itemListMapper.mapList(
                mutableListOf(
                    ItemDBOFake.provideItemDBO(1),
                    ItemDBOFake.provideItemDBO(1)
                )
            )
        } returns mutableListOf(
            ItemEntityFake.provideItemEntity(1),
            ItemEntityFake.provideItemEntity(1)
        )
        val either = repoRepository.getChampById(id)

        coVerify(exactly = 1) { itemDaoEntityMapper.map(itemListResponse) }
        coVerify(exactly = 1) { itemDAO.insertItems(listItemDBO) }
        coVerify(exactly = 1) { champDAO.getChampById(id) }
        coVerify(exactly = 1) { itemDAO.getItemByListId(champDBO.suitableItem.split(",")) }
        coVerify(exactly = 1) {
            itemListMapper.mapList(
                mutableListOf(
                    ItemDBOFake.provideItemDBO(1),
                    ItemDBOFake.provideItemDBO(1)
                )
            )
        }

        either.either(
            failAction = { Assert.assertTrue(false) },
            successAction = { actual -> Assert.assertEquals(expected, actual) }
        )
    }

    @Test
    fun `getChampById itemDAO-getAllItem-not-empty success`() = runBlocking {
        //given
        val id = "11"
        val listItemDBO = ItemDBOFake.provideListItemResponse(10)
        val champDBO = ChampDBOFake.provideChampDbo(1)
        val expected = ChampListEntity.Champ(
            id = champDBO.id,
            cost = champDBO.cost,
            linkImg = champDBO.linkImg,
            activated = champDBO.activated,
            linkChampCover = champDBO.linkChampCover,
            originAndClassName = champDBO.originAndClassName.split(","),
            skillName = champDBO.skillName,
            linkSkillAvatar = champDBO.linkSkillAvatar,
            rankChamp = champDBO.rankChamp,
            suitableItem = mutableListOf(
                ItemEntityFake.provideItemEntity(1),
                ItemEntityFake.provideItemEntity(1)
            ),
            threeStar = false,
            name = champDBO.name
        )
        coEvery { itemDAO.getAllItem() } returns listItemDBO
        coEvery { champDAO.getChampById(id) } returns champDBO
        coEvery { itemDAO.getItemByListId(champDBO.suitableItem.split(",")) } returns mutableListOf(
            ItemDBOFake.provideItemDBO(1),
            ItemDBOFake.provideItemDBO(1)
        )
        every {
            itemListMapper.mapList(
                mutableListOf(
                    ItemDBOFake.provideItemDBO(1),
                    ItemDBOFake.provideItemDBO(1)
                )
            )
        } returns mutableListOf(
            ItemEntityFake.provideItemEntity(1),
            ItemEntityFake.provideItemEntity(1)
        )
        //when
        val either = repoRepository.getChampById(id)
        //then
        coVerify(exactly = 1) { champDAO.getChampById(id) }
        coVerify(exactly = 1) { itemDAO.getItemByListId(champDBO.suitableItem.split(",")) }
        coVerify(exactly = 1) {
            itemListMapper.mapList(
                mutableListOf(
                    ItemDBOFake.provideItemDBO(1),
                    ItemDBOFake.provideItemDBO(1)
                )
            )
        }
        either.either(
            failAction = { Assert.assertTrue(false) },
            successAction = { actual -> Assert.assertEquals(expected, actual) }
        )
    }

    @Test
    fun `getChampById apiService-getItemListResponse-error`() = runBlocking {
        //given
        val id = "11"
        val exception = Exception()
        val expected = CommonFake.provideFailure(exception)
        coEvery { itemDAO.getAllItem() } returns listOf()
        coEvery { apiService.getItemListResponse() } throws exception
        every { remoteExceptionInterceptor.handleException(exception) } returns expected
        //when
        val either = repoRepository.getChampById(id)
        //then
        either.either(
            failAction = { actual -> Assert.assertEquals(expected, actual) },
            successAction = { Assert.assertTrue(false) }
        )
    }

    @Test
    fun `getChampById champDAO-getChampById-error`() = runBlocking {
        //given
        val id = "11"
        val exception = Exception()
        val expected = CommonFake.provideFailure(exception)
        val listItemDBO = ItemDBOFake.provideListItemResponse(10)
        coEvery { itemDAO.getAllItem() } returns listItemDBO
        coEvery { champDAO.getChampById(id) } throws exception
        every { remoteExceptionInterceptor.handleException(exception) } returns expected
        //when
        val either = repoRepository.getChampById(id)
        //then
        either.either(
            failAction = { actual -> Assert.assertEquals(expected, actual) },
            successAction = { Assert.assertTrue(false) }
        )
    }

    @Test
    fun `getChampById itemDao-getChampByListId-error`() = runBlocking {
        //given
        val id = "11"
        val exception = Exception()
        val expected = CommonFake.provideFailure(exception)
        val listItemDBO = ItemDBOFake.provideListItemResponse(10)
        val champDBO = ChampDBOFake.provideChampDbo(1)
        coEvery { itemDAO.getAllItem() } returns listItemDBO
        coEvery { champDAO.getChampById(id) } returns champDBO
        coEvery { itemDAO.getItemByListId(champDBO.suitableItem.split(",")) } throws exception
        every { remoteExceptionInterceptor.handleException(exception) } returns expected
        //when
        val either = repoRepository.getChampById(id)
        //then
        either.either(
            failAction = { actual -> Assert.assertEquals(expected, actual) },
            successAction = { Assert.assertTrue(false) }
        )
    }

    @Test
    fun `getClassAndOriginContent classAndOriginDAO-getAllClassAndOrigin-not-empty `() =
        runBlocking {
            val listClassAndOriginName = listOf<String>("name0", "name1", "name2")
            coEvery { classAndOriginDAO.getAllClassAndOrigin() } returns ClassAndOriginDBOFake.provideListClassAndOriginDBOEmpty(
                10
            )

            val listClassAndOriginExpected =
                mutableListOf<ClassAndOriginListEntity.ClassAndOrigin>()
            listClassAndOriginName.forEach { classOrOriginName ->
                val champListDboByName =
                    ChampDBOFake.provideChampDBOListEmptyByName(2, classOrOriginName)
                val champListEntityByName =
                    ChampEntityFake.provideChampEntityListByName(2, classOrOriginName)
                coEvery { classAndOriginDAO.getClassOrOriginByName(classOrOriginName) } returns ClassAndOriginDBOFake.provideClassAndOriginDBO(
                    0
                )
                coEvery { champDAO.getListChampByClassOrOriginName(classOrOriginName) } returns champListDboByName
                coEvery {
                    champListMapper.mapList(
                        champListDboByName
                    )
                } returns champListEntityByName
                listClassAndOriginExpected.add(
                    ClassAndOriginListEntity.ClassAndOrigin(
                        classOrOriginName = ClassAndOriginDBOFake.provideClassAndOriginDBO(0).classOrOriginName,
                        listChamp = ChampListEntity(
                            champListEntityByName
                        ),
                        content = ClassAndOriginDBOFake.provideClassAndOriginDBO(0).content,
                        bonus = ClassAndOriginDBOFake.provideClassAndOriginDBO(0).bonus
                    )
                )
            }
            val expected = ClassAndOriginListEntity(listClassAndOrigin = listClassAndOriginExpected)

            val either = repoRepository.getClassAndOriginContent(listClassAndOriginName)

            listClassAndOriginName.forEach { classOrOriginName ->
                val champListDboByName =
                    ChampDBOFake.provideChampDBOListEmptyByName(2, classOrOriginName)
                coVerify(exactly = 1) { classAndOriginDAO.getClassOrOriginByName(classOrOriginName) }
                coVerify(exactly = 1) { champDAO.getListChampByClassOrOriginName(classOrOriginName) }
                coVerify(exactly = 1) {
                    champListMapper.mapList(
                        champListDboByName
                    )
                }
            }
            either.either(
                failAction = { Assert.assertTrue(false) },
                successAction = { actual -> Assert.assertEquals(expected, actual) }
            )
        }

    @Test
    fun `getClassAndOriginContent classAndOriginDAO-getAllClassAndOrigin-empty `() = runBlocking {
        val listClassAndOriginName = listOf<String>("name0", "name1", "name2")
        val listClassAndOriginResponse =
            ClassAndOriginListResponseFake.provideListClassAndOriginEmpty(3)
        val classAndOriginResponse =
            ClassAndOriginListResponse(FeedClassAndOrigin(classAndOrigins = listClassAndOriginResponse))
        val listClassAndOriginDBO = ClassAndOriginDBOFake.provideListClassAndOriginDBOEmpty(3)
        val classAndOriginDBO = ClassAndOriginListDBO(classAndOrigins = listClassAndOriginDBO)
        coEvery { classAndOriginDAO.getAllClassAndOrigin() } returns listOf()
        coEvery { apiService.getClassAndOriginList() } returns classAndOriginResponse
        coEvery { classAndOriginDaoEntityMapper.map(classAndOriginResponse) } returns classAndOriginDBO
        coEvery { classAndOriginDAO.insertClassAndOrigin(classAndOriginDBO.classAndOrigins) } returns Unit
        val listClassAndOriginExpected = mutableListOf<ClassAndOriginListEntity.ClassAndOrigin>()
        listClassAndOriginName.forEach { classOrOriginName ->
            val champEntityListByName =
                ChampEntityFake.provideChampEntityListByName(3, classOrOriginName)
            val champDBOListEmptyByName =
                ChampDBOFake.provideChampDBOListEmptyByName(3, classOrOriginName)
            coEvery { classAndOriginDAO.getClassOrOriginByName(classOrOriginName) } returns ClassAndOriginDBOFake.provideClassAndOriginDBO(
                0
            )
            coEvery { champDAO.getListChampByClassOrOriginName(classOrOriginName) } returns champDBOListEmptyByName

            coEvery {
                champListMapper.mapList(champDBOListEmptyByName)
            } returns champEntityListByName
            listClassAndOriginExpected.add(
                ClassAndOriginListEntity.ClassAndOrigin(
                    classOrOriginName = ClassAndOriginDBOFake.provideClassAndOriginDBO(0).classOrOriginName,
                    listChamp = ChampListEntity(
                        champs = champEntityListByName
                    ),
                    content = ClassAndOriginDBOFake.provideClassAndOriginDBO(0).content,
                    bonus = ClassAndOriginDBOFake.provideClassAndOriginDBO(0).bonus
                )
            )
        }
        val expected = ClassAndOriginListEntity(listClassAndOrigin = listClassAndOriginExpected)

        val either = repoRepository.getClassAndOriginContent(listClassAndOriginName)

        coVerify(exactly = 1) { classAndOriginDAO.getAllClassAndOrigin() }
        coVerify(exactly = 1) { apiService.getClassAndOriginList() }
        coVerify(exactly = 1) { classAndOriginDaoEntityMapper.map(classAndOriginResponse) }
        coVerify(exactly = 1) { classAndOriginDAO.insertClassAndOrigin(listClassAndOriginDBO) }
        listClassAndOriginName.forEach { classOrOriginName ->
            val champDBOListEmptyByName =
                ChampDBOFake.provideChampDBOListEmptyByName(3, classOrOriginName)
            coVerify(exactly = 1) { classAndOriginDAO.getClassOrOriginByName(classOrOriginName) }
            coVerify(exactly = 1) { champDAO.getListChampByClassOrOriginName(classOrOriginName) }
            coVerify(exactly = 1) {
                champListMapper.mapList(
                    champDBOListEmptyByName
                )
            }
        }
        either.either(
            failAction = { Assert.assertTrue(false) },
            successAction = { actual -> Assert.assertEquals(expected, actual) }
        )
    }

    @Test
    fun `getClassAndOriginContent apiService-getClassAndOriginList-error`() = runBlocking {
        //given
        val listClassAndOriginName = listOf<String>("name0", "name1", "name2")
        val exception = Exception()
        val expected = CommonFake.provideFailure(exception)
        coEvery { classAndOriginDAO.getAllClassAndOrigin() } returns listOf()
        coEvery { apiService.getClassAndOriginList() } throws exception
        every { remoteExceptionInterceptor.handleException(exception) } returns expected
        //when
        val either = repoRepository.getClassAndOriginContent(listClassAndOriginName)
        //then
        either.either(
            failAction = { actual -> Assert.assertEquals(expected, actual) },
            successAction = { Assert.assertTrue(false) }
        )
    }
}