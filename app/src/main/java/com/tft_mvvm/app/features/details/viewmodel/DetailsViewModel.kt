package com.tft_mvvm.app.features.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.details.mapper.ClassAndOriginContentMapper
import com.tft_mvvm.app.features.details.mapper.ItemHeaderMapper
import com.tft_mvvm.app.features.details.mapper.TeamRecommendForChampMapper
import com.tft_mvvm.app.features.details.model.ChampDetailsModel
import com.tft_mvvm.data.common.AppDispatchers
import com.tft_mvvm.domain.features.usecase.GetChampByIdUseCase
import com.tft_mvvm.domain.features.usecase.GetClassAndOriginContentUseCase
import com.tft_mvvm.domain.features.usecase.GetTeamRecommendForChampUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val getChampByIdUseCase: GetChampByIdUseCase,
    private val getClassAndOriginContentUseCase: GetClassAndOriginContentUseCase,
    private val getTeamRecommendForChampUseCase: GetTeamRecommendForChampUseCase,
    private val teamRecommendForChampMapper: TeamRecommendForChampMapper,
    private val itemHeaderMapper: ItemHeaderMapper,
    private val appDispatchers: AppDispatchers,
    private val classAndOriginContentMapper: ClassAndOriginContentMapper
) : BaseViewModel() {
    private val champDetailsModelLiveData = MutableLiveData<ChampDetailsModel>()
    private val listClassAndOriginContent = mutableListOf<ChampDetailsModel.ClassAndOriginContent>()
    private val listTeamRecommend = mutableListOf<ChampDetailsModel.TeamRecommend>()

    private var champDetailsModel =
        ChampDetailsModel(
            headerModel = null,
            listItem = listOf(),
            listTeamRecommend = listOf()
        )

    fun getChampById(id: String) =
        viewModelScope.launch(appDispatchers.main) {
            val dbResult = withContext(appDispatchers.io) {
                getChampByIdUseCase.execute(
                    GetChampByIdUseCase.GetChampByIdUseCaseParam(
                        id
                    )
                )
            }
            dbResult.either({
                updateChampDetails(null)
            }) {
                updateChampDetails(
                    champDetailsModel.copy(
                        headerModel = itemHeaderMapper.map(it)
                    )
                )
                getListClassAndOriginContent(it.originAndClassName)
                getTeamRecommendForChampLiveData(it.id)
            }
        }


    private fun getListClassAndOriginContent(
        listClassAndOriginName: List<String>
    ) =
        viewModelScope.launch(appDispatchers.main) {
            val dbResult = withContext(appDispatchers.io) {
                getClassAndOriginContentUseCase.execute(
                    GetClassAndOriginContentUseCase.GetClassAnOriginContentParam(
                        listClassOrOriginName = listClassAndOriginName
                    )
                )
            }
            dbResult.either({
                updateChampDetails(
                    champDetailsModel.copy(
                        listItem = listOf()
                    )
                )
            }) {
                updateChampDetails(
                    champDetailsModel.copy(
                        listItem = classAndOriginContentMapper.mapList(it.listClassAndOrigin)
                    )
                )
            }
        }


    private fun getTeamRecommendForChampLiveData(id: String) =
        viewModelScope.launch(appDispatchers.main) {
            val dbResult = withContext(appDispatchers.io) {
                getTeamRecommendForChampUseCase.execute(
                    GetTeamRecommendForChampUseCase.GetTeamRecommendForChampUseCaseParam(
                        id = id
                    )
                )
            }
            dbResult.either({
                updateChampDetails(
                    champDetailsModel.copy(
                        listTeamRecommend = listOf()
                    )
                )
            }) {
                updateChampDetails(
                    champDetailsModel.copy(
                        listTeamRecommend = teamRecommendForChampMapper.mapList(it.teamBuilders)
                    )
                )
            }
        }

    private fun updateChampDetails(champDetails: ChampDetailsModel?) {
        champDetails?.let {
            champDetailsModel = it
        }
        champDetailsModelLiveData.value = champDetailsModel
    }

    fun getChampDetailsLiveData(): LiveData<ChampDetailsModel> {
        return champDetailsModelLiveData
    }
}
