package com.tft_mvvm.app.features.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.common_jvm.function.Either
import com.tft_mvvm.app.features.fake.ChampEntityFake
import com.tft_mvvm.app.features.fake.ChampForTeamFake
import com.tft_mvvm.app.features.main.adapter.AdapterShowRecommendTeamBuilder
import com.tft_mvvm.app.features.main.mapper.TeamBuilderRecommendMapper
import com.tft_mvvm.data.common.AppDispatchers
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.model.TeamBuilderListEntity
import com.tft_mvvm.domain.features.usecase.GetListTeamBuilderUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShowTeamRecommendViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var getListTeamBuilderUseCase: GetListTeamBuilderUseCase
    private val appDispatchers = AppDispatchers(Dispatchers.Unconfined, Dispatchers.Unconfined)
    private lateinit var teamBuilderRecommendMapper: TeamBuilderRecommendMapper
    private lateinit var showTeamRecommendViewModel: ShowTeamRecommendViewModel

    @Before
    fun setUp() {
        getListTeamBuilderUseCase = mockk()
        teamBuilderRecommendMapper = mockk()
        showTeamRecommendViewModel = ShowTeamRecommendViewModel(
            getListTeamBuilderUseCase = getListTeamBuilderUseCase,
            appDispatchers = appDispatchers,
            teamBuilderRecommendMapper = teamBuilderRecommendMapper
        )
    }

    private val listTeamBuilderFake = listOf(
        AdapterShowRecommendTeamBuilder.TeamBuilder(
            name = "S",
            listChamp = ChampForTeamFake.provideListChampForTeam(10)
        ),
        AdapterShowRecommendTeamBuilder.TeamBuilder(
            name = "A",
            listChamp = ChampForTeamFake.provideListChampForTeam(10)
        ),
        AdapterShowRecommendTeamBuilder.TeamBuilder(
            name = "B",
            listChamp = ChampForTeamFake.provideListChampForTeam(10)
        ),
        AdapterShowRecommendTeamBuilder.TeamBuilder(
            name = "C",
            listChamp = ChampForTeamFake.provideListChampForTeam(10)
        )
    )

    private val listTeamEntityFake = listOf(
        TeamBuilderListEntity.TeamsBuilder(
            name = "S",
            champEntity = ChampListEntity(ChampEntityFake.provideChampEntityListHaveItem(10))
        ),
        TeamBuilderListEntity.TeamsBuilder(
            name = "A",
            champEntity = ChampListEntity(ChampEntityFake.provideChampEntityListHaveItem(10))
        ),
        TeamBuilderListEntity.TeamsBuilder(
            name = "B",
            champEntity = ChampListEntity(ChampEntityFake.provideChampEntityListHaveItem(10))
        ),
        TeamBuilderListEntity.TeamsBuilder(
            name = "C",
            champEntity = ChampListEntity(ChampEntityFake.provideChampEntityListHaveItem(10))
        )
    )


    @Test
    fun `getTeam success`() {
        val observerResult =
            mockk<Observer<List<AdapterShowRecommendTeamBuilder.TeamBuilder>>>(relaxed = true)
        val result = Either.Success(TeamBuilderListEntity(listTeamEntityFake))

        coEvery {
            getListTeamBuilderUseCase.execute(
                GetListTeamBuilderUseCase.GetTeamUseCaseParam(
                    true
                )
            )
        } returns result

        every { teamBuilderRecommendMapper.mapList(listTeamEntityFake) } returns listTeamBuilderFake

        showTeamRecommendViewModel.getListTeamBuilderLiveData().observeForever(observerResult)

        showTeamRecommendViewModel.getListTeamBuilder(true)

        verify { observerResult.onChanged(listTeamBuilderFake) }

        confirmVerified(observerResult)
    }


}