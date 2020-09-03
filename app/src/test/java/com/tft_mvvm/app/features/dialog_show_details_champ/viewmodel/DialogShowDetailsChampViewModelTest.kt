package com.tft_mvvm.app.features.dialog_show_details_champ.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.app.features.dialog_show_details_champ.mapper.ChampDialogModelMapper
import com.tft_mvvm.app.features.dialog_show_details_champ.model.ChampDialogModel
import com.tft_mvvm.app.features.fake.ChampDialogFake
import com.tft_mvvm.app.features.fake.ChampEntityFake
import com.tft_mvvm.data.common.AppDispatchers
import com.tft_mvvm.domain.features.usecase.GetChampByIdUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DialogShowDetailsChampViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var getChampByIdUseCase: GetChampByIdUseCase
    private lateinit var champDialogModelMapper: ChampDialogModelMapper
    private lateinit var viewModel: DialogShowDetailsChampViewModel
    private val appDispatchers = AppDispatchers(Dispatchers.Unconfined, Dispatchers.Unconfined)

    @Before
    fun setup() {
        getChampByIdUseCase = mockk()
        champDialogModelMapper = mockk()
        viewModel = DialogShowDetailsChampViewModel(
            getChampByIdUseCase = getChampByIdUseCase,
            appDispatchers = appDispatchers,
            champDialogModelMapper = champDialogModelMapper
        )
    }

    @Test
    fun `getChampById success`() {
        //GIVEN
        val observerResult = mockk<Observer<ChampDialogModel>>(relaxed = true)
        val champEntity = ChampEntityFake.provideChampEntity("1")
        val result = Either.Success(champEntity)
        val champDialogModel = ChampDialogFake.provideChampDialog("1")

        coEvery { getChampByIdUseCase.execute(GetChampByIdUseCase.GetChampByIdUseCaseParam("1")) } returns result
        every { champDialogModelMapper.map(champEntity) } returns champDialogModel
        viewModel.getChampByDialogLiveData().observeForever(observerResult)

        //WHEN
        viewModel.getChampById("1")
        //THEN
        verify {
            observerResult.onChanged(champDialogModel)
        }
        confirmVerified(observerResult)
    }

    @Test
    fun `getChampById error`() {
        val observerResult = mockk<Observer<ChampDialogModel>>(relaxed = true)
        val error = Either.Fail(Failure.ConnectionTimeout)
        viewModel.getChampByDialogLiveData().observeForever(observerResult)

        coEvery { getChampByIdUseCase.execute(GetChampByIdUseCase.GetChampByIdUseCaseParam("1")) } returns error

        viewModel.getChampById("1")

        verify {
            observerResult.onChanged(null)
        }

        confirmVerified(observerResult)
    }
}