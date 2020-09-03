package com.tft_mvvm.app.features.details.viewmodel


import androidx.lifecycle.Observer
import com.example.common_jvm.function.Either
import com.tft_mvvm.app.features.details.mapper.ClassAndOriginContentMapper
import com.tft_mvvm.app.features.details.mapper.ItemHeaderMapper
import com.tft_mvvm.app.features.details.mapper.TeamRecommendForChampMapper
import com.tft_mvvm.app.features.fake.ChampEntityFake
import com.tft_mvvm.app.model.Champ
import com.tft_mvvm.data.common.AppDispatchers
import com.tft_mvvm.domain.features.usecase.GetChampByIdUseCase
import com.tft_mvvm.domain.features.usecase.GetClassAndOriginContentUseCase
import com.tft_mvvm.domain.features.usecase.GetTeamRecommendForChampUseCase
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {
    private val getChampByIdUseCase: GetChampByIdUseCase = mockk()
    private val getClassAndOriginContentUseCase: GetClassAndOriginContentUseCase = mockk()
    private val getTeamRecommendForChampUseCase: GetTeamRecommendForChampUseCase = mockk()
    private val teamRecommendForChampMapper: TeamRecommendForChampMapper = mockk()
    private val itemHeaderMapper: ItemHeaderMapper = mockk()
    private val appDispatchers : AppDispatchers= mockk()
    private val classAndOriginContentMapper: ClassAndOriginContentMapper = mockk()

    val detailsViewModel = DetailsViewModel(
        getChampByIdUseCase = getChampByIdUseCase,
        getClassAndOriginContentUseCase = getClassAndOriginContentUseCase,
        getTeamRecommendForChampUseCase = getTeamRecommendForChampUseCase,
        teamRecommendForChampMapper = teamRecommendForChampMapper,
        itemHeaderMapper = itemHeaderMapper,
        appDispatchers = appDispatchers,
        classAndOriginContentMapper = classAndOriginContentMapper
    )

//    @Rule
//    @JvmField
//    //val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getChampById() {
        val id = "1"

        val result = Either.Success(ChampEntityFake.provideChampEntity("1"))
        coEvery { getChampByIdUseCase.execute(GetChampByIdUseCase.GetChampByIdUseCaseParam(id)) } returns result

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