package com.tft_mvvm.app.features.details.viewmodel

//import com.tft_mvvm.app.mapper.ClassOrOriginMapper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.details.mapper.ClassAndOriginContentMapper
import com.tft_mvvm.app.features.details.mapper.ItemHeaderMapper
import com.tft_mvvm.app.features.details.mapper.TeamRecommendForChampMapper
import com.tft_mvvm.app.features.details.model.ChampDetailsModel
import com.tft_mvvm.domain.features.usecase.GetChampByIdUseCase
import com.tft_mvvm.domain.features.usecase.GetClassAndOriginContentUseCase
import com.tft_mvvm.domain.features.usecase.GetTeamRecommendForChampUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val getChampByIdUseCase: GetChampByIdUseCase,
    private val getClassAndOriginContentUseCase: GetClassAndOriginContentUseCase,
    private val getTeamRecommendForChampUseCase: GetTeamRecommendForChampUseCase,
    private val teamRecommendForChampMapper: TeamRecommendForChampMapper,
    private val itemHeaderMapper: ItemHeaderMapper,
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
        viewModelScope.launch(Dispatchers.Main) {
            val dbResult = withContext(Dispatchers.IO) {
                getChampByIdUseCase.execute(
                    GetChampByIdUseCase.GetChampByIdUseCaseParam(
                        id
                    )
                )
            }
            dbResult.either({
                //TODO handel error
                Log.d("phuc", "$it")
            }) {
                updateChampDetails(
                    champDetailsModel.copy(
                        headerModel = itemHeaderMapper.map(it)
                    )
                )
                val listClassAndOriginName = it.originAndClassName
                getListClassAndOriginContent(listClassAndOriginName)
                getTeamRecommendForChampLiveData(it.id)
            }
        }


    private fun getListClassAndOriginContent(
        listClassAndOriginName: List<String>
    ) =
        viewModelScope.launch(Dispatchers.Main) {
            val dbResult = withContext(Dispatchers.IO) {
                getClassAndOriginContentUseCase.execute(
                    GetClassAndOriginContentUseCase.GetClassAnOriginContentParam(
                        listClassOrOriginName = listClassAndOriginName
                    )
                )
            }
            dbResult.either({
                //TODO handle error
                Log.d("Phuc", "$it")
            }) {
                updateListItemModel(classAndOriginContentMapper.mapList(it.listClassAndOrigin))
            }
        }


    private fun getTeamRecommendForChampLiveData(id: String) =
        viewModelScope.launch(Dispatchers.Main) {
            val dbResult = withContext(Dispatchers.IO) {
                getTeamRecommendForChampUseCase.execute(
                    GetTeamRecommendForChampUseCase.GetTeamRecommendForChampUseCaseParam(
                        id = id
                    )
                )
            }
            dbResult.either({
                //TODO handle error
            }) {
                updateListTeamRecommend(teamRecommendForChampMapper.mapList(it.teamBuilders))
            }
        }

    private fun updateListItemModel(item: List<ChampDetailsModel.ClassAndOriginContent>) {
        listClassAndOriginContent.clear()
        listClassAndOriginContent.addAll(item)
        updateChampDetails(
            champDetailsModel.copy(
                listItem = listClassAndOriginContent
            )
        )
    }

    private fun updateListTeamRecommend(listTeam: List<ChampDetailsModel.TeamRecommend>) {
        listTeamRecommend.clear()
        listTeamRecommend.addAll(listTeam)
        updateChampDetails(
            champDetailsModel.copy(
                listTeamRecommend = listTeamRecommend
            )
        )
    }

    private fun updateChampDetails(champDetails: ChampDetailsModel) {
        champDetailsModel = champDetails
        champDetailsModelLiveData.value = champDetailsModel
    }

    fun getChampDetailsLiveData(): LiveData<ChampDetailsModel> {
        return champDetailsModelLiveData
    }
}