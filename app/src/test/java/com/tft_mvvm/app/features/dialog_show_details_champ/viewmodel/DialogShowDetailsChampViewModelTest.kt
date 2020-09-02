package com.tft_mvvm.app.features.dialog_show_details_champ.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.common_jvm.function.Either
import com.tft_mvvm.app.features.dialog_show_details_champ.mapper.ChampDialogModelMapper
import com.tft_mvvm.app.features.fake.ChampEntityFake
import com.tft_mvvm.app.features.fake.ChampFake
import com.tft_mvvm.app.model.Champ
import com.tft_mvvm.domain.features.usecase.GetChampByIdUseCase
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.util.*

class DialogShowDetailsChampViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var getChampByIdUseCase: GetChampByIdUseCase
    private lateinit var champDialogModelMapper: ChampDialogModelMapper
    private lateinit var viewModel: DialogShowDetailsChampViewModel
    @Before
    fun setup() {
        getChampByIdUseCase = mockk()
        champDialogModelMapper = mockk()
        viewModel = DialogShowDetailsChampViewModel(
            getChampByIdUseCase = getChampByIdUseCase,
            champDialogModelMapper = champDialogModelMapper
        )
    }

    @Test
    fun getChampById() {
        val id = "1"
        val champEntity = ChampEntityFake.provideChampEntity("1")
        val champ = ChampFake.provideChamp("1")
        val observerResult = mockk<Observer<Champ>>()
        val result = Either.Success(champ)

    }

    @Test
    fun getChampByDialogLiveData() {
    }
}