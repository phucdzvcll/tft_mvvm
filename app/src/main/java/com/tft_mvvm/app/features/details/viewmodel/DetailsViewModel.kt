package com.tft_mvvm.app.features.details.viewmodel

//import com.tft_mvvm.app.mapper.ClassOrOriginMapper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.details.mapper.ChampOfChampDetailsMapper
import com.tft_mvvm.app.features.details.mapper.ItemHeaderMapper
import com.tft_mvvm.app.features.details.mapper.TeamRecommendForChampMapper
import com.tft_mvvm.app.features.details.model.ChampDetailsModel
import com.tft_mvvm.domain.features.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val getChampByIdUseCase: GetChampByIdUseCase,
    private val getListChampsByClassUseCase: GetListChampsByClassUseCase,
    private val getListChampsByOriginUseCase: GetListChampsByOriginUseCase,
    private val getClassAndOriginContentUseCase: GetClassAndOriginContentUseCase,
    private val getTeamRecommendForChampUseCase: GetTeamRecommendForChampUseCase,
    private val teamRecommendForChampMapper: TeamRecommendForChampMapper,
    private val itemHeaderMapper: ItemHeaderMapper,
    private val champMapper: ChampOfChampDetailsMapper
) : BaseViewModel() {
    private val champDetailsModelLiveData = MutableLiveData<ChampDetailsModel>()
    private val headerModelLiveData = MutableLiveData<ChampDetailsModel.HeaderModel>()

    private val listClassAndOriginContent = mutableListOf<ChampDetailsModel.ClassAndOriginContent>()
    private val listTeamRecommend = mutableListOf<ChampDetailsModel.TeamRecommend>()

    private var champDetailsModel = ChampDetailsModel(
        ChampDetailsModel.HeaderModel(
            name = "",
            cost = "",
            activated = "",
            linkChampCover = "",
            listSuitableItem = listOf(),
            linkAvatarSkill = "",
            nameSkill = ""
        ),
        listItem = listOf(),
        listTeamRecommend = listOf()
    )
    private var itemOriginModel = ChampDetailsModel.ClassAndOriginContent(
        ChampDetailsModel.ClassAndOriginContent.ClassOrOrigin(
            classOrOriginName = "",
            bonus = listOf(),
            content = ""
        ),
        listOf()
    )
    private var itemClassModel = ChampDetailsModel.ClassAndOriginContent(
        ChampDetailsModel.ClassAndOriginContent.ClassOrOrigin(
            classOrOriginName = "",
            bonus = listOf(),
            content = ""
        ),
        listOf()
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
            }) {
                updateChampDetails(
                    champDetailsModel.copy(
                        headerModel = itemHeaderMapper.map(it)
                    )
                )
                headerModelLiveData.value = itemHeaderMapper.map(it)
                getOriginContent(it.origin, true)
                getClassContent((it.classs.split(","))[0], true)
                if ((it.classs.split(",")).size > 1) {
                    getClassContent((it.classs.split(","))[1], true)
                }
                getTeamRecommendForChampLiveData(it.id)
            }
        }


    private fun getOriginContent(name: String, isForceLoadData: Boolean) =
        viewModelScope.launch(Dispatchers.Main) {
            val dbResult = withContext(Dispatchers.IO) {
                getClassAndOriginContentUseCase.execute(
                    GetClassAndOriginContentUseCase.GetClassAnOriginContentParam(
                        isForceLoadData = isForceLoadData,
                        classOrOriginName = name
                    )
                )
            }
            dbResult.either({
                //TODO handle error
            }) {
                updateItemOriginHolderViewHolder(
                    itemOriginModel.copy(
                        classOrOrigin = ChampDetailsModel.ClassAndOriginContent.ClassOrOrigin(
                            classOrOriginName = it.classOrOriginName,
                            content = it.content,
                            bonus = it.bonus.split(",")
                        )
                    )
                )
                getListChampsByOrigin(it.classOrOriginName)
            }
        }

    private fun getListChampsByOrigin(origin: String) = viewModelScope.launch(Dispatchers.Main) {
        val dbResult = withContext(Dispatchers.IO) {
            getListChampsByOriginUseCase.execute(
                GetListChampsByOriginUseCase.GetChampsByOriginUseCaseParam(
                    origin = origin
                )
            )
        }
        dbResult.either({
            //TODO handle error
        }) {
            updateListItemModel(
                itemOriginModel.copy(listChamp = champMapper.mapList(it.champs))
            )
        }
    }

    private fun getClassContent(name: String, isForceLoadData: Boolean) =
        viewModelScope.launch(Dispatchers.Main) {
            val dbResult = withContext(Dispatchers.IO) {
                getClassAndOriginContentUseCase.execute(
                    GetClassAndOriginContentUseCase.GetClassAnOriginContentParam(
                        isForceLoadData = isForceLoadData,
                        classOrOriginName = name
                    )
                )
            }
            dbResult.either({
                //TODO handle error
                Log.d("Phuc", "$it")
            }) {
                updateItemClassHolderViewHolder(
                    itemClassModel.copy(
                        classOrOrigin = ChampDetailsModel.ClassAndOriginContent.ClassOrOrigin(
                            classOrOriginName = it.classOrOriginName,
                            bonus = it.bonus.split(","),
                            content = it.content
                        )
                    )
                )
                getListChampsByClass(it.classOrOriginName)
            }
        }

    private fun getListChampsByClass(classs: String) = viewModelScope.launch(Dispatchers.Main) {
        val dbResult = withContext(Dispatchers.IO) {
            getListChampsByClassUseCase.execute(
                GetListChampsByClassUseCase.GetChampsByClassUseCaseParam(
                    classs = classs
                )
            )
        }
        dbResult.either({
            //TODO handle error
        }) {
            updateListItemModel(
                itemClassModel.copy(
                    listChamp = champMapper.mapList(it.champs)
                )
            )
        }
    }

    fun getTeamRecommendForChampLiveData(id: String) =
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


    private fun updateItemOriginHolderViewHolder(model: ChampDetailsModel.ClassAndOriginContent) {
        itemOriginModel = model
    }

    private fun updateItemClassHolderViewHolder(model: ChampDetailsModel.ClassAndOriginContent) {
        itemClassModel = model
    }

    fun getHeaderViewHolderModel(): LiveData<ChampDetailsModel.HeaderModel> {
        return headerModelLiveData
    }

    private fun updateListItemModel(item: ChampDetailsModel.ClassAndOriginContent) {
        listClassAndOriginContent.add(item)
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