package com.tft_mvvm.data.features.champs.repository

import com.tft_mvvm.data.exception_interceptor.RemoteExceptionInterceptor
import com.tft_mvvm.data.fake.*
import com.tft_mvvm.data.features.champs.mapper.*
import com.tft_mvvm.data.features.champs.remote.*
import com.tft_mvvm.data.features.champs.repository.fake.*
import com.tft_mvvm.data.features.champs.service.ApiService
import com.tft_mvvm.data.local.database.ChampDAO
import com.tft_mvvm.data.local.database.ClassAndOriginDAO
import com.tft_mvvm.data.local.database.ItemDAO
import com.tft_mvvm.data.local.database.TeamDAO
import com.tft_mvvm.data.local.model.ChampListDBO
import com.tft_mvvm.data.local.model.ClassAndOriginListDBO
import com.tft_mvvm.data.local.model.ItemListDBO
import com.tft_mvvm.data.local.model.TeamListDBO
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.model.ClassAndOriginListEntity
import com.tft_mvvm.domain.features.model.TeamBuilderListEntity
import com.tft_mvvm.domain.features.model.TeamListEntity
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
        coVerify(exactly = 1) { apiService.getChampList() }
        verify(exactly = 1) { champDaoEntityMapper.map(champListResponse) }
        coVerify(exactly = 1) { champDAO.deleteAllChampTable() }
        coVerify(exactly = 1) { champDAO.insertChamps(champListDBO.champDBOs) }
        coVerify(exactly = 1) { champDAO.getAllChamp() }
        verify(exactly = 1) { champListMapper.mapList(champListDBO.champDBOs) }
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
        coVerify(exactly = 1) { apiService.getChampList() }
        verify(exactly = 1) { champDaoEntityMapper.map(champListResponse) }
        coVerify(exactly = 1) { champDAO.deleteAllChampTable() }
        coVerify(exactly = 1) { champDAO.insertChamps(champListDBO.champDBOs) }
        coVerify(exactly = 2) { champDAO.getAllChamp() }
        verify(exactly = 1) { champListMapper.mapList(champListDBO.champDBOs) }
    }

    @Test
    fun `getAllChamp isForceLoadData-false localChamps-not-empty`() = runBlocking {
        //given
        val isForceLoadData = false
        val champListDBO = ChampListDBO(champDBOs = ChampDBOFake.provideChampDBOList(10))
        val champListEntity = ChampEntityFake.provideChampEntityList(10)

        coEvery { champDAO.getAllChamp() } returns champListDBO.champDBOs
        every { champListMapper.mapList(champListDBO.champDBOs) } returns champListEntity

        //when
        repoRepository.getAllChamps(isForceLoadData)

        //then
        verify(exactly = 1) { champListMapper.mapList(champListDBO.champDBOs) }
        coVerify(exactly = 2) { champDAO.getAllChamp() }
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
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
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
            star = "1",
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
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
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
            star = "1",
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
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
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
    fun `getClassAndOriginContent classAndOriginDAO-getAllClassAndOrigin-not-empty `() =
        runBlocking {
            val listClassAndOriginName = listOf("name0", "name1", "name2")
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
                        champEntity = ChampListEntity(
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
                    champEntity = ChampListEntity(
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

    @Test
    fun getListItem() = runBlocking {
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
        val expected = ItemDBOFake.provideListItemDBO(5)
        val listId = listOf<String>("item0", "item1", "item2", "item3", "item4")
        val listItemEntity = ItemEntityFake.provideListItemEntity(5)

        every { itemListMapper.mapList(expected) } returns listItemEntity

        repoRepository.getListItem(listItemDBO, listId)

        coVerify(exactly = 1) { itemListMapper.mapList(expected) }
    }

    @Test
    fun `getListItem-success`() = runBlocking {
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
        val listItemDBOResult = ItemDBOFake.provideListItemDBO(5)
        val listId = listOf<String>("item0", "item1", "item2", "item3", "item4")
        val expected = ItemEntityFake.provideListItemEntity(5)

        every { itemListMapper.mapList(listItemDBOResult) } returns expected

        val actual = repoRepository.getListItem(listItemDBO, listId)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `getChampById Internal actual-not-empty`() = runBlocking {
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        val id = "1233"
        val expected = ChampDBOFake.provideChampDbo(3)

        val actual = repoRepository.getChampByIdInternal(listChampDBO, id)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `getChampById Internal actual-empty`() = runBlocking {
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        val id = "1"
        val expected = ChampDBOFake.provideChampDboEmpty()

        val actual = repoRepository.getChampByIdInternal(listChampDBO, id)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `getListChampByListIdInternal actual-not-Empty`() = runBlocking {
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        val listId = listOf<String>("1230", "1231", "1232", "1233", "1234")
        val expected = ChampDBOFake.provideChampDBOList(5)

        val actual = repoRepository.getListChampByListIdInternal(listChampDBO, listId)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `getListChampByListIdInternal actual-Empty`() = runBlocking {
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        val listId = listOf<String>("1", "2", "3")
        val expected = listOf<ChampListDBO.ChampDBO>()

        val actual = repoRepository.getListChampByListIdInternal(listChampDBO, listId)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `mapListTeam dbTeamListEntity-empty `() = runBlocking {
        val dbTeamListEntity = TeamListEntity(listOf())
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        val expected = listOf<TeamBuilderListEntity.TeamsBuilder>()
        val actual = repoRepository.mapListTeam(dbTeamListEntity, listItemDBO, listChampDBO)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `mapListTeam dbTeamListEntity-not-empty listIdChamp-empty`() = runBlocking {
        val dbTeamListEntity = TeamFake.provideListTeamListChampEmpty(10)
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        val expected = mutableListOf<TeamBuilderListEntity.TeamsBuilder>()
        for (team in dbTeamListEntity.teams) {
            expected.add(
                TeamBuilderListEntity.TeamsBuilder(
                    name = team.nameTeam,
                    champEntity = ChampListEntity(listOf())
                )
            )
        }

        val actual = repoRepository.mapListTeam(dbTeamListEntity, listItemDBO, listChampDBO)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `mapListTeam dbTeamListEntity-not-empty listIdChamp-not-empty`() = runBlocking {
        val dbTeamListEntity = TeamFake.provideListTeam(10)
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        val expected = mutableListOf<TeamBuilderListEntity.TeamsBuilder>()
        val listChampEntityResult = mutableListOf<ChampListEntity.Champ>()
        listChampEntityResult.addAll(ChampEntityFake.provideListChampEntityForMapListTeam(2))
        listChampEntityResult.add(ChampEntityFake.provideChampEntityThreeStar(2))
        listChampEntityResult.add(ChampEntityFake.provideChampEntityThreeStar(3))
        for (team in dbTeamListEntity.teams) {
            expected.add(
                TeamBuilderListEntity.TeamsBuilder(
                    name = team.nameTeam,
                    champEntity = ChampListEntity(champs = listChampEntityResult)
                )
            )
        }
        every { itemListMapper.mapList(listOf()) } returns listOf()
        every { itemListMapper.mapList(ItemDBOFake.provideListItemDBO(2)) } returns ItemEntityFake.provideListItemEntity(
            2
        )
        val actual = repoRepository.mapListTeam(dbTeamListEntity, listItemDBO, listChampDBO)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `getTeams isForceLoadData-true itemDAO-not-Empty`() = runBlocking {
        val isForLoadData = true
        val teamResponse = TeamListResponse(FeedTeam(TeamResponseFake.provideTeamResponseList(10)))
        val teamDBO = TeamListDBO(teamDBOs = TeamDBOFake.provideTeamDBOList(10))
        val teamListEntity = TeamFake.provideListTeam(10)
        val listItem = ItemDBOFake.provideListItemDBO(10)
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        every { itemListMapper.mapList(listOf()) } returns listOf()
        every { itemListMapper.mapList(ItemDBOFake.provideListItemDBO(2)) } returns ItemEntityFake.provideListItemEntity(
            2
        )
        coEvery { apiService.getTeamList() } returns teamResponse
        every { teamDaoEntityMapper.map(teamResponse) } returns teamDBO
        coEvery { teamDAO.deleteAllTeam() } returns Unit
        coEvery { teamDAO.insertTeam(teamDBO.teamDBOs) } returns Unit
        coEvery { teamDAO.getAllTeam() } returns teamDBO.teamDBOs
        every { teamListMapper.mapList(teamDBO.teamDBOs) } returns teamListEntity.teams
        coEvery { itemDAO.getAllItem() } returns listItem
        coEvery { champDAO.getAllChamp() } returns listChampDBO
        coEvery { itemDAO.getAllItem() } returns listItemDBO
        repoRepository.getTeams(isForLoadData)

        coVerify(exactly = 1) { apiService.getTeamList() }
        coVerify(exactly = 1) { teamDaoEntityMapper.map(teamResponse) }
        coVerify(exactly = 1) { teamDAO.deleteAllTeam() }
        coVerify(exactly = 1) { teamDAO.insertTeam(teamDBO.teamDBOs) }
        coVerify(exactly = 1) { teamDAO.getAllTeam() }
        coVerify(exactly = 1) { teamListMapper.mapList(teamDBO.teamDBOs) }
        coVerify(exactly = 2) { itemDAO.getAllItem() }
        coVerify(exactly = 1) { champDAO.getAllChamp() }
    }

    @Test
    fun `getTeams isForceLoadData-true itemDAO-Empty`() = runBlocking {
        val isForLoadData = true
        val teamResponse = TeamListResponse(FeedTeam(TeamResponseFake.provideTeamResponseList(10)))
        val teamDBO = TeamListDBO(teamDBOs = TeamDBOFake.provideTeamDBOList(10))
        val teamListEntity = TeamFake.provideListTeam(10)
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        val listItemResponse = ItemResponseFake.provideListItemResponse(10)
        every { itemListMapper.mapList(listOf()) } returns listOf()
        every { itemListMapper.mapList(ItemDBOFake.provideListItemDBO(2)) } returns ItemEntityFake.provideListItemEntity(
            2
        )
        coEvery { apiService.getTeamList() } returns teamResponse
        every { teamDaoEntityMapper.map(teamResponse) } returns teamDBO
        coEvery { teamDAO.deleteAllTeam() } returns Unit
        coEvery { teamDAO.insertTeam(teamDBO.teamDBOs) } returns Unit
        coEvery { teamDAO.getAllTeam() } returns teamDBO.teamDBOs
        every { teamListMapper.mapList(teamDBO.teamDBOs) } returns teamListEntity.teams
        coEvery { itemDAO.getAllItem() } returnsMany listOf(listOf(), listItemDBO)
        coEvery { champDAO.getAllChamp() } returns listChampDBO
        coEvery { apiService.getItemListResponse() } returns ItemListResponse(
            FeedItem(
                listItemResponse
            )
        )
        every { itemDaoEntityMapper.map(ItemListResponse(FeedItem(listItemResponse))) } returns ItemListDBO(
            listItemDBO
        )
        coEvery { itemDAO.insertItems(listItemDBO) } returns Unit
        repoRepository.getTeams(isForLoadData)

        coVerify(exactly = 1) { apiService.getTeamList() }
        coVerify(exactly = 1) { teamDaoEntityMapper.map(teamResponse) }
        coVerify(exactly = 1) { teamDAO.deleteAllTeam() }
        coVerify(exactly = 1) { teamDAO.insertTeam(teamDBO.teamDBOs) }
        coVerify(exactly = 1) { teamDAO.getAllTeam() }
        coVerify(exactly = 1) { teamListMapper.mapList(teamDBO.teamDBOs) }
        coVerify(exactly = 2) { itemDAO.getAllItem() }
        coVerify(exactly = 1) { apiService.getItemListResponse() }
        coVerify(exactly = 1) { itemDAO.insertItems(listItemDBO) }
        coVerify(exactly = 1) { champDAO.getAllChamp() }
    }

    @Test
    fun `getTeams isForceLoadData-false itemDAO-Empty`() = runBlocking {
        val isForLoadData = false
        val teamResponse = TeamListResponse(FeedTeam(TeamResponseFake.provideTeamResponseList(10)))
        val teamDBO = TeamListDBO(teamDBOs = TeamDBOFake.provideTeamDBOList(10))
        val teamListEntity = TeamFake.provideListTeam(10)
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        every { itemListMapper.mapList(listOf()) } returns listOf()
        every { itemListMapper.mapList(ItemDBOFake.provideListItemDBO(2)) } returns ItemEntityFake.provideListItemEntity(
            2
        )
        coEvery { teamDAO.getAllTeam() } returnsMany listOf(listOf(), teamDBO.teamDBOs)
        coEvery { apiService.getTeamList() } returns teamResponse
        every { teamDaoEntityMapper.map(teamResponse) } returns teamDBO
        coEvery { teamDAO.deleteAllTeam() } returns Unit
        coEvery { teamDAO.insertTeam(teamDBO.teamDBOs) } returns Unit
        every { teamListMapper.mapList(teamDBO.teamDBOs) } returns teamListEntity.teams
        coEvery { itemDAO.getAllItem() } returns listItemDBO
        coEvery { champDAO.getAllChamp() } returns listChampDBO

        repoRepository.getTeams(isForLoadData)

        coVerify(exactly = 1) { apiService.getTeamList() }
        coVerify(exactly = 1) { teamDaoEntityMapper.map(teamResponse) }
        coVerify(exactly = 1) { teamDAO.deleteAllTeam() }
        coVerify(exactly = 1) { teamDAO.insertTeam(teamDBO.teamDBOs) }
        coVerify(exactly = 2) { teamDAO.getAllTeam() }
        coVerify(exactly = 1) { teamListMapper.mapList(teamDBO.teamDBOs) }
        coVerify(exactly = 2) { itemDAO.getAllItem() }
        coVerify(exactly = 1) { champDAO.getAllChamp() }
    }

    @Test
    fun `getTeams isForceLoadData-false itemDAO-not-Empty`() = runBlocking {
        val isForLoadData = false
        val teamDBO = TeamListDBO(teamDBOs = TeamDBOFake.provideTeamDBOList(10))
        val teamListEntity = TeamFake.provideListTeam(10)
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        every { itemListMapper.mapList(listOf()) } returns listOf()
        every { itemListMapper.mapList(ItemDBOFake.provideListItemDBO(2)) } returns ItemEntityFake.provideListItemEntity(
            2
        )
        coEvery { teamDAO.getAllTeam() } returns teamDBO.teamDBOs
        every { teamListMapper.mapList(teamDBO.teamDBOs) } returns teamListEntity.teams
        coEvery { itemDAO.getAllItem() } returns listItemDBO
        coEvery { champDAO.getAllChamp() } returns listChampDBO

        repoRepository.getTeams(isForLoadData)

        coVerify(exactly = 2) { teamDAO.getAllTeam() }
        coVerify(exactly = 1) { teamListMapper.mapList(teamDBO.teamDBOs) }
        coVerify(exactly = 2) { itemDAO.getAllItem() }
        coVerify(exactly = 1) { champDAO.getAllChamp() }
    }

    @Test
    fun `getTeams success `() = runBlocking {
        val isForceLoadData = false
        val dbTeamListEntity = TeamFake.provideListTeam(10)
        val teamDBO = TeamListDBO(teamDBOs = TeamDBOFake.provideTeamDBOList(10))
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        val listTeamBuilder = mutableListOf<TeamBuilderListEntity.TeamsBuilder>()
        val listChampEntityResult = mutableListOf<ChampListEntity.Champ>()
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
        listChampEntityResult.addAll(ChampEntityFake.provideListChampEntityForMapListTeam(2))
        listChampEntityResult.add(ChampEntityFake.provideChampEntityThreeStar(2))
        listChampEntityResult.add(ChampEntityFake.provideChampEntityThreeStar(3))
        for (team in dbTeamListEntity.teams) {
            listTeamBuilder.add(
                TeamBuilderListEntity.TeamsBuilder(
                    name = team.nameTeam,
                    champEntity = ChampListEntity(champs = listChampEntityResult)
                )
            )
        }
        val expected = TeamBuilderListEntity(teamBuilders = listTeamBuilder)
        every { itemListMapper.mapList(listOf()) } returns listOf()
        every { itemListMapper.mapList(ItemDBOFake.provideListItemDBO(2)) } returns ItemEntityFake.provideListItemEntity(
            2
        )
        coEvery { teamDAO.getAllTeam() } returns teamDBO.teamDBOs
        every { teamListMapper.mapList(teamDBO.teamDBOs) } returns dbTeamListEntity.teams
        coEvery { itemDAO.getAllItem() } returns listItemDBO
        coEvery { champDAO.getAllChamp() } returns listChampDBO
        val either = repoRepository.getTeams(isForceLoadData)

        either.either(
            failAction = { Assert.assertTrue(false) },
            successAction = { actual -> Assert.assertEquals(expected, actual) }
        )
    }

    @Test
    fun `getTeam-apiService-getTeamList-error`() = runBlocking {
        //given
        val isForceLoadData = true
        val exception = Exception()
        val expected = CommonFake.provideFailure(exception)
        coEvery { apiService.getTeamList() } throws exception
        every { remoteExceptionInterceptor.handleException(exception) } returns expected

        //when
        val allChamps = repoRepository.getTeams(isForceLoadData)

        //then
        allChamps.either(
            failAction = { actual -> Assert.assertEquals(expected, actual) },
            successAction = { Assert.assertTrue(false) }
        )
    }

    @Test
    fun `getTeam-apiService--error`() = runBlocking {
        //given
        val isForceLoadData = false
        val exception = Exception()
        val expected = CommonFake.provideFailure(exception)
        coEvery { teamDAO.getAllTeam() } throws exception
        every { remoteExceptionInterceptor.handleException(exception) } returns expected

        //when
        val allChamps = repoRepository.getTeams(isForceLoadData)

        //then
        allChamps.either(
            failAction = { actual -> Assert.assertEquals(expected, actual) },
            successAction = { Assert.assertTrue(false) }
        )
    }

    @Test
    fun `getTeamRecommendForChamp teamDao-getAllTeam-empty id-not-empty`() = runBlocking {
        val id = "1230"
        val teamResponse = TeamListResponse(FeedTeam(TeamResponseFake.provideTeamResponseList(10)))
        val teamDBO = TeamListDBO(teamDBOs = TeamDBOFake.provideTeamDBOList(10))
        val teamListEntity = TeamFake.provideListTeam(10)
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        every { itemListMapper.mapList(listOf()) } returns listOf()
        every { itemListMapper.mapList(ItemDBOFake.provideListItemDBO(2)) } returns ItemEntityFake.provideListItemEntity(
            2
        )
        coEvery { teamDAO.getAllTeam() } returnsMany listOf(listOf(), teamDBO.teamDBOs)
        coEvery { apiService.getTeamList() } returns teamResponse
        every { teamDaoEntityMapper.map(teamResponse) } returns teamDBO
        coEvery { teamDAO.deleteAllTeam() } returns Unit
        coEvery { teamDAO.insertTeam(teamDBO.teamDBOs) } returns Unit
        every { teamListMapper.mapList(teamDBO.teamDBOs) } returns teamListEntity.teams
        coEvery { itemDAO.getAllItem() } returns listItemDBO
        coEvery { champDAO.getAllChamp() } returns listChampDBO
        repoRepository.getTeamRecommendForChamp(id)

        coVerify(exactly = 1) { apiService.getTeamList() }
        coVerify(exactly = 1) { teamDaoEntityMapper.map(teamResponse) }
        coVerify(exactly = 1) { teamDAO.deleteAllTeam() }
        coVerify(exactly = 1) { teamDAO.insertTeam(teamDBO.teamDBOs) }
        coVerify(exactly = 2) { teamDAO.getAllTeam() }
        coVerify(exactly = 1) { teamListMapper.mapList(teamDBO.teamDBOs) }
        coVerify(exactly = 1) { itemDAO.getAllItem() }
        coVerify(exactly = 1) { champDAO.getAllChamp() }
    }

    @Test
    fun `getTeamRecommendForChamp teamDao-getAllTeam-empty id-empty`() = runBlocking {
        val id = ""
        val teamResponse = TeamListResponse(FeedTeam(TeamResponseFake.provideTeamResponseList(10)))
        val teamDBO = TeamListDBO(teamDBOs = TeamDBOFake.provideTeamDBOList(10))
        val teamListEntity = TeamFake.provideListTeam(10)
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        every { itemListMapper.mapList(listOf()) } returns listOf()
        every { itemListMapper.mapList(ItemDBOFake.provideListItemDBO(2)) } returns ItemEntityFake.provideListItemEntity(
            2
        )
        coEvery { teamDAO.getAllTeam() } returnsMany listOf(listOf(), teamDBO.teamDBOs)
        coEvery { apiService.getTeamList() } returns teamResponse
        every { teamDaoEntityMapper.map(teamResponse) } returns teamDBO
        coEvery { teamDAO.deleteAllTeam() } returns Unit
        coEvery { teamDAO.insertTeam(teamDBO.teamDBOs) } returns Unit
        every { teamListMapper.mapList(teamDBO.teamDBOs) } returns teamListEntity.teams
        coEvery { itemDAO.getAllItem() } returns listItemDBO
        coEvery { champDAO.getAllChamp() } returns listChampDBO
        repoRepository.getTeamRecommendForChamp(id)
        coVerify(exactly = 1) { apiService.getTeamList() }
        coVerify(exactly = 1) { teamDaoEntityMapper.map(teamResponse) }
        coVerify(exactly = 1) { teamDAO.deleteAllTeam() }
        coVerify(exactly = 1) { teamDAO.insertTeam(teamDBO.teamDBOs) }
        coVerify(exactly = 2) { teamDAO.getAllTeam() }
        coVerify(exactly = 1) { teamListMapper.mapList(teamDBO.teamDBOs) }
        coVerify(exactly = 1) { itemDAO.getAllItem() }
        coVerify(exactly = 1) { champDAO.getAllChamp() }
    }

    @Test
    fun `getTeamRecommendForChamp teamDao-getAllTeam-not-empty id-empty`() = runBlocking {
        val id = ""
        val teamResponse = TeamListResponse(FeedTeam(TeamResponseFake.provideTeamResponseList(10)))
        val teamDBO = TeamListDBO(teamDBOs = TeamDBOFake.provideTeamDBOList(10))
        val teamListEntity = TeamFake.provideListTeam(10)
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        every { itemListMapper.mapList(listOf()) } returns listOf()
        every { itemListMapper.mapList(ItemDBOFake.provideListItemDBO(2)) } returns ItemEntityFake.provideListItemEntity(
            2
        )
        coEvery { teamDAO.getAllTeam() } returns teamDBO.teamDBOs
        every { teamDaoEntityMapper.map(teamResponse) } returns teamDBO
        every { teamListMapper.mapList(teamDBO.teamDBOs) } returns teamListEntity.teams
        coEvery { itemDAO.getAllItem() } returns listItemDBO
        coEvery { champDAO.getAllChamp() } returns listChampDBO

        repoRepository.getTeamRecommendForChamp(id)

        coVerify(exactly = 2) { teamDAO.getAllTeam() }
        coVerify(exactly = 1) { teamListMapper.mapList(teamDBO.teamDBOs) }
        coVerify(exactly = 1) { itemDAO.getAllItem() }
        coVerify(exactly = 1) { champDAO.getAllChamp() }
    }

    @Test
    fun `getTeamRecommendForChamp teamDao-getAllTeam-not-empty id-not-empty`() = runBlocking {
        val id = "1237"
        val teamResponse = TeamListResponse(FeedTeam(TeamResponseFake.provideTeamResponseList(10)))
        val teamDBO = TeamListDBO(teamDBOs = TeamDBOFake.provideTeamDBOList(10))
        val teamListEntity = TeamFake.provideListTeam(10)
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        every { itemListMapper.mapList(listOf()) } returns listOf()
        every { itemListMapper.mapList(ItemDBOFake.provideListItemDBO(2)) } returns ItemEntityFake.provideListItemEntity(
            2
        )
        coEvery { teamDAO.getAllTeam() } returns teamDBO.teamDBOs
        every { teamDaoEntityMapper.map(teamResponse) } returns teamDBO
        every { teamListMapper.mapList(teamDBO.teamDBOs) } returns teamListEntity.teams
        coEvery { itemDAO.getAllItem() } returns listItemDBO
        coEvery { champDAO.getAllChamp() } returns listChampDBO

        repoRepository.getTeamRecommendForChamp(id)

        coVerify(exactly = 2) { teamDAO.getAllTeam() }
        coVerify(exactly = 1) { teamListMapper.mapList(teamDBO.teamDBOs) }
        coVerify(exactly = 1) { itemDAO.getAllItem() }
        coVerify(exactly = 1) { champDAO.getAllChamp() }
    }

    @Test
    fun `getTeamRecommendForChamp success`() = runBlocking {
        val id = "1231"
        val teamResponse = TeamListResponse(FeedTeam(TeamResponseFake.provideTeamResponseList(10)))
        val teamDBO = TeamListDBO(teamDBOs = TeamDBOFake.provideTeamDBOList(10))
        val expected = TeamFake.provideListTeam(10)
        val listItemDBO = ItemDBOFake.provideListItemDBO(10)
        val listChampDBO = ChampDBOFake.provideChampDBOList(10)
        every { itemListMapper.mapList(listOf()) } returns listOf()
        every { itemListMapper.mapList(ItemDBOFake.provideListItemDBO(2)) } returns ItemEntityFake.provideListItemEntity(
            2
        )
        coEvery { teamDAO.getAllTeam() } returns teamDBO.teamDBOs
        every { teamDaoEntityMapper.map(teamResponse) } returns teamDBO
        every { teamListMapper.mapList(teamDBO.teamDBOs) } returns expected.teams
        coEvery { itemDAO.getAllItem() } returns listItemDBO
        coEvery { champDAO.getAllChamp() } returns listChampDBO

        val either = repoRepository.getTeamRecommendForChamp(id)

    }

}

