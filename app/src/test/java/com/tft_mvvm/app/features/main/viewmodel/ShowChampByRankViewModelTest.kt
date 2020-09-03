package com.tft_mvvm.app.features.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.app.features.fake.ChampEntityFake
import com.tft_mvvm.app.features.fake.ChampFake
import com.tft_mvvm.app.mapper.ChampMapper
import com.tft_mvvm.app.model.Champ
import com.tft_mvvm.data.common.AppDispatchers
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.usecase.GetListChampsUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShowChampByRankViewModelTest() {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var champMapper: ChampMapper
    private lateinit var listChampsUseCase: GetListChampsUseCase
    private lateinit var showChampByRankViewModel: ShowChampByRankViewModel
    private val appDispatchers = AppDispatchers(Dispatchers.Unconfined, Dispatchers.Unconfined)

    @Before
    fun setup() {
        listChampsUseCase = mockk()
        champMapper = mockk()
        showChampByRankViewModel = ShowChampByRankViewModel(
            listChampsUseCase = listChampsUseCase,
            appDispatchers = appDispatchers,
            champMapper = champMapper
        )
    }

    @Test
    fun `getChamps isForceLoadData-true`() {
        val observerResult = mockk<Observer<List<Champ>>>(relaxed = true)
        val listChampEntity = ChampEntityFake.provideChampEntityList(10)
        val result = Either.Success(ChampListEntity(listChampEntity))
        val listChamp = ChampFake.provideListChamp(10)

        coEvery { listChampsUseCase.execute(GetListChampsUseCase.GetAllChampUseCaseParam(true)) } returns result
        every { champMapper.mapList(listChampEntity) } returns listChamp
        showChampByRankViewModel.getChampsLiveData().observeForever(observerResult)

        showChampByRankViewModel.getChamps(true)

        verify {
            observerResult.onChanged(listChamp)
        }

        confirmVerified(observerResult)
    }

    @Test
    fun `getChamps isForceLoadData-false`() {
        val observerResult = mockk<Observer<List<Champ>>>(relaxed = true)
        val listChampEntity = ChampEntityFake.provideChampEntityList(10)
        val result = Either.Success(ChampListEntity(listChampEntity))
        val listChamp = ChampFake.provideListChamp(10)

        coEvery { listChampsUseCase.execute(GetListChampsUseCase.GetAllChampUseCaseParam(false)) } returns result
        every { champMapper.mapList(listChampEntity) } returns listChamp
        showChampByRankViewModel.getChampsLiveData().observeForever(observerResult)

        showChampByRankViewModel.getChamps(false)

        verify {
            observerResult.onChanged(listChamp)
        }

        confirmVerified(observerResult)
    }

    @Test
    fun `getChamps error`() {
        val observerResult = mockk<Observer<List<Champ>>>(relaxed = true)
        val error = Either.Fail(Failure.InternetError)

        coEvery { listChampsUseCase.execute(GetListChampsUseCase.GetAllChampUseCaseParam(true)) } returns error

        showChampByRankViewModel.getChampsLiveData().observeForever(observerResult)

        showChampByRankViewModel.getChamps(true)

        verify {
            observerResult.onChanged(null)
        }

        confirmVerified(observerResult)
    }
}