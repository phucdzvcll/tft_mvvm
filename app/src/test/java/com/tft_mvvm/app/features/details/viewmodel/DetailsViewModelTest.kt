package com.tft_mvvm.app.features.details.viewmodel

import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tft_mvvm.app.features.details.mapper.ClassAndOriginContentMapper
import com.tft_mvvm.app.features.details.mapper.ItemHeaderMapper
import com.tft_mvvm.app.features.details.mapper.TeamRecommendForChampMapper
import com.tft_mvvm.domain.features.usecase.GetChampByIdUseCase
import com.tft_mvvm.domain.features.usecase.GetClassAndOriginContentUseCase
import com.tft_mvvm.domain.features.usecase.GetTeamRecommendForChampUseCase
import org.junit.Rule
import io.mockk.*

class DetailsViewModelTest {
    private val getChampByIdUseCase: GetChampByIdUseCase = mockk()
    private val getClassAndOriginContentUseCase: GetClassAndOriginContentUseCase = mockk()
    private val getTeamRecommendForChampUseCase: GetTeamRecommendForChampUseCase = mockk()
    private val teamRecommendForChampMapper: TeamRecommendForChampMapper = mockk()
    private val itemHeaderMapper: ItemHeaderMapper = mockk()
    private val classAndOriginContentMapper: ClassAndOriginContentMapper = mockk()

    val detailsViewModel = DetailsViewModel(
        getChampByIdUseCase = getChampByIdUseCase,
        getClassAndOriginContentUseCase = getClassAndOriginContentUseCase,
        getTeamRecommendForChampUseCase = getTeamRecommendForChampUseCase,
        teamRecommendForChampMapper = teamRecommendForChampMapper,
        itemHeaderMapper = itemHeaderMapper,
        classAndOriginContentMapper = classAndOriginContentMapper
    )

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getChampById() {
    }

    @Test
    fun getChampDetailsLiveData() {
    }

    data class ChampDetailsModel(
        val headerModel: HeaderModel?,
        val listItem: List<ClassAndOriginContent>,
        val listTeamRecommend: List<TeamRecommend>
    ) {
        data class HeaderModel(
            val nameSkill: String,
            val activated: String,
            val linkAvatarSkill: String,
            val linkChampCover: String,
            val name: String,
            val cost: String,
            val listSuitableItem: List<Item>
        )

        data class ClassAndOriginContent(
            val listChamp: List<Champ>,
            val classOrOriginName: String,
            val bonus: List<String>,
            val content: String
        )

        data class TeamRecommend(
            val name: String,
            val listChamp: List<Champ>
        )

        data class Champ(
            val id: String,
            val name: String,
            val imgUrl: String,
            val cost: String,
            val threeStar: String,
            val itemSuitable: List<Item>
        )

        data class Item(
            val id: String,
            val itemName: String,
            val itemAvatar: String
        )
    }
}