package com.tft_mvvm.app.features.details.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.app.features.details.mapper.ClassAndOriginContentMapper
import com.tft_mvvm.app.features.details.mapper.ItemHeaderMapper
import com.tft_mvvm.app.features.details.mapper.TeamRecommendForChampMapper
import com.tft_mvvm.app.features.details.model.ChampDetailsModel
import com.tft_mvvm.app.features.fake.ChampEntityFake
import com.tft_mvvm.app.features.fake.ChampForDetailsFake
import com.tft_mvvm.app.features.fake.ItemForDetailsFake
import com.tft_mvvm.data.common.AppDispatchers
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.model.ClassAndOriginListEntity
import com.tft_mvvm.domain.features.model.TeamBuilderListEntity
import com.tft_mvvm.domain.features.usecase.GetChampByIdUseCase
import com.tft_mvvm.domain.features.usecase.GetClassAndOriginContentUseCase
import com.tft_mvvm.domain.features.usecase.GetTeamRecommendForChampUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {
    private val getChampByIdUseCase: GetChampByIdUseCase = mockk()
    private val getClassAndOriginContentUseCase: GetClassAndOriginContentUseCase = mockk()
    private val getTeamRecommendForChampUseCase: GetTeamRecommendForChampUseCase = mockk()
    private val teamRecommendForChampMapper: TeamRecommendForChampMapper = mockk()
    private val itemHeaderMapper: ItemHeaderMapper = mockk()
    private val appDispatchers: AppDispatchers =
        AppDispatchers(Dispatchers.Unconfined, Dispatchers.Unconfined)
    private val classAndOriginContentMapper: ClassAndOriginContentMapper = mockk()
    private val detailsViewModel = DetailsViewModel(
        getChampByIdUseCase = getChampByIdUseCase,
        getClassAndOriginContentUseCase = getClassAndOriginContentUseCase,
        getTeamRecommendForChampUseCase = getTeamRecommendForChampUseCase,
        teamRecommendForChampMapper = teamRecommendForChampMapper,
        itemHeaderMapper = itemHeaderMapper,
        appDispatchers = appDispatchers,
        classAndOriginContentMapper = classAndOriginContentMapper
    )
    private val listClassAndOriginEntityFake = listOf(
        ClassAndOriginListEntity.ClassAndOrigin(
            classOrOriginName = "class1",
            bonus = "2:111111,4:222222",
            content = "xxxxxxx",
            champEntity = ChampListEntity(ChampEntityFake.provideChampEntityListByClass(5, 1))
        ),
        ClassAndOriginListEntity.ClassAndOrigin(
            classOrOriginName = "origin1",
            bonus = "3:111111,6:222222",
            content = "xxxxxxx",
            champEntity = ChampListEntity(ChampEntityFake.provideChampEntityListByOrigin(5, 1))
        )
    )
    private val classContentFake = ChampDetailsModel.ClassAndOriginContent(
        classOrOriginName = "class1",
        bonus = listOf("2:111111,4:222222"),
        content = "xxxxxxx",
        listChamp = ChampForDetailsFake.provideListChampForDetailsChamp(5)
    )
    private val originContentFake = ChampDetailsModel.ClassAndOriginContent(
        classOrOriginName = "origin1",
        bonus = listOf("3:111111,6:222222"),
        content = "xxxxxxx",
        listChamp = ChampForDetailsFake.provideListChampForDetailsChamp(5)
    )
    private val headerModel = ChampDetailsModel.HeaderModel(
        name = "name1",
        activated = "activated 1",
        linkChampCover = "http://linkchamp1.google/",
        cost = "11",
        linkAvatarSkill = "http://linkskill1.google/",
        listSuitableItem = listOf(
            ItemForDetailsFake.provideItemForDetailsChamp(2),
            ItemForDetailsFake.provideItemForDetailsChamp(3),
            ItemForDetailsFake.provideItemForDetailsChamp(4)
        ),
        nameSkill = "skill 1"
    )
    private val listTeam = listOf(
        ChampDetailsModel.TeamRecommend(
            name = "S",
            listChamp = ChampForDetailsFake.provideListChampForDetailsChampHaveItem(8)
        ),
        ChampDetailsModel.TeamRecommend(
            name = "A",
            listChamp = ChampForDetailsFake.provideListChampForDetailsChampHaveItem(8)
        ),
        ChampDetailsModel.TeamRecommend(
            name = "B",
            listChamp = ChampForDetailsFake.provideListChampForDetailsChampHaveItem(8)
        ), ChampDetailsModel.TeamRecommend(
            name = "C",
            listChamp = ChampForDetailsFake.provideListChampForDetailsChampHaveItem(8)
        )
    )
    private val listTeamEntityFake = listOf(
        TeamBuilderListEntity.TeamsBuilder(
            name = "S",
            champEntity = ChampListEntity(ChampEntityFake.provideChampEntityListHaveItem(8))
        ),
        TeamBuilderListEntity.TeamsBuilder(
            name = "A",
            champEntity = ChampListEntity(ChampEntityFake.provideChampEntityListHaveItem(8))
        ),
        TeamBuilderListEntity.TeamsBuilder(
            name = "B",
            champEntity = ChampListEntity(ChampEntityFake.provideChampEntityListHaveItem(8))
        ),
        TeamBuilderListEntity.TeamsBuilder(
            name = "C",
            champEntity = ChampListEntity(ChampEntityFake.provideChampEntityListHaveItem(8))
        )
    )

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    @Test
    fun `getChampById success`() {
        //GIVEN
        val observerResult = mockk<Observer<ChampDetailsModel>>(relaxed = true)
        val champEntity = ChampEntityFake.provideChampEntity("1")
        val result = Either.Success(champEntity)

        coEvery { getChampByIdUseCase.execute(GetChampByIdUseCase.GetChampByIdUseCaseParam("id_1")) } returns result
        every { itemHeaderMapper.map(champEntity) } returns headerModel
        val classAndOriginListEntity = ClassAndOriginListEntity(listClassAndOriginEntityFake)
        coEvery {
            getClassAndOriginContentUseCase.execute(
                GetClassAndOriginContentUseCase.GetClassAnOriginContentParam(
                    champEntity.originAndClassName
                )
            )
        } returns Either.Success(classAndOriginListEntity)

        every { classAndOriginContentMapper.mapList(listClassAndOriginEntityFake) } returns listOf(
            classContentFake,
            originContentFake
        )
        coEvery {
            getTeamRecommendForChampUseCase.execute(
                GetTeamRecommendForChampUseCase.GetTeamRecommendForChampUseCaseParam(
                    champEntity.id
                )
            )
        } returns Either.Success(TeamBuilderListEntity(listTeamEntityFake))
        every { teamRecommendForChampMapper.mapList(listTeamEntityFake) } returns listTeam
        detailsViewModel.getChampDetailsLiveData().observeForever(observerResult)
        //WHEN
        detailsViewModel.getChampById("id_1")
        //THEN
        verify { observerResult.onChanged(ChampDetailsModel(headerModel, listOf(), listOf())) }
        verify {
            observerResult.onChanged(
                ChampDetailsModel(
                    headerModel,
                    listOf(classContentFake, originContentFake),
                    listOf()
                )
            )
        }
        verify {
            observerResult.onChanged(
                ChampDetailsModel(
                    headerModel,
                    listOf(classContentFake, originContentFake),
                    listTeam
                )
            )
        }

        confirmVerified(observerResult)
    }

    @Test
    fun `getChampById success getClassAndOrigin-error`() {
        //GIVEN
        val observerResult = mockk<Observer<ChampDetailsModel>>(relaxed = true)
        val champEntity = ChampEntityFake.provideChampEntity("1")
        val result = Either.Success(champEntity)
        val error = Either.Fail(Failure.ConnectionTimeout)

        coEvery { getChampByIdUseCase.execute(GetChampByIdUseCase.GetChampByIdUseCaseParam("id_1")) } returns result
        every { itemHeaderMapper.map(champEntity) } returns headerModel
        coEvery {
            getClassAndOriginContentUseCase.execute(
                GetClassAndOriginContentUseCase.GetClassAnOriginContentParam(
                    champEntity.originAndClassName
                )
            )
        } returns error
        coEvery {
            getTeamRecommendForChampUseCase.execute(
                GetTeamRecommendForChampUseCase.GetTeamRecommendForChampUseCaseParam(
                    champEntity.id
                )
            )
        } returns Either.Success(TeamBuilderListEntity(listTeamEntityFake))
        every { teamRecommendForChampMapper.mapList(listTeamEntityFake) } returns listTeam
        detailsViewModel.getChampDetailsLiveData().observeForever(observerResult)
        //WHEN
        detailsViewModel.getChampById("id_1")
        //THEN
        verify { observerResult.onChanged(ChampDetailsModel(headerModel, listOf(), listOf())) }
        verify {
            observerResult.onChanged(
                ChampDetailsModel(
                    headerModel,
                    listOf(),
                    listTeam
                )
            )
        }

        confirmVerified(observerResult)
    }

    @Test
    fun `getChampById success getListTeam-error`() {
        //GIVEN
        val observerResult = mockk<Observer<ChampDetailsModel>>(relaxed = true)
        val champEntity = ChampEntityFake.provideChampEntity("1")
        val result = Either.Success(champEntity)
        val error = Either.Fail(Failure.ApiError(400,"error"))
        val classAndOriginListEntity = ClassAndOriginListEntity(listClassAndOriginEntityFake)

        coEvery { getChampByIdUseCase.execute(GetChampByIdUseCase.GetChampByIdUseCaseParam("id_1")) } returns result
        every { itemHeaderMapper.map(champEntity) } returns headerModel
        coEvery {
            getClassAndOriginContentUseCase.execute(
                GetClassAndOriginContentUseCase.GetClassAnOriginContentParam(
                    champEntity.originAndClassName
                )
            )
        } returns Either.Success(classAndOriginListEntity)

        every { classAndOriginContentMapper.mapList(listClassAndOriginEntityFake) } returns listOf(
            classContentFake,
            originContentFake
        )

        coEvery {
            getTeamRecommendForChampUseCase.execute(
                GetTeamRecommendForChampUseCase.GetTeamRecommendForChampUseCaseParam(
                    champEntity.id
                )
            )
        } returns error

        detailsViewModel.getChampDetailsLiveData().observeForever(observerResult)
        //WHEN
        detailsViewModel.getChampById("id_1")
        //THEN
        verify { observerResult.onChanged(ChampDetailsModel(headerModel, listOf(), listOf())) }
        verify {
            observerResult.onChanged(
                ChampDetailsModel(
                    headerModel,
                    listOf(classContentFake,originContentFake),
                    listOf()
                )
            )
        }

        confirmVerified(observerResult)
    }

    @Test
    fun `getChampById error`() {
        val observerResult = mockk<Observer<ChampDetailsModel>>(relaxed = true)
        val error = Either.Fail(Failure.ConnectionTimeout)
        detailsViewModel.getChampDetailsLiveData().observeForever(observerResult)
        coEvery { getChampByIdUseCase.execute(GetChampByIdUseCase.GetChampByIdUseCaseParam("id_1")) } returns error
        confirmVerified(observerResult)
    }
}