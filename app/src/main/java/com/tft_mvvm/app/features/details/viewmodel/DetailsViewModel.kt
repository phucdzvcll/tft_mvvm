package com.tft_mvvm.app.features.details.viewmodel

//import com.tft_mvvm.app.mapper.ClassOrOriginMapper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.details.mapper.ItemHeaderMapper
import com.tft_mvvm.app.features.details.mapper.TeamRecommendForChampMapper
import com.tft_mvvm.app.features.details.model.HeaderViewHolderModel
import com.tft_mvvm.app.features.details.model.ItemHolderViewHolder
import com.tft_mvvm.app.features.details.model.ItemRv
import com.tft_mvvm.app.features.main.mapper.ChampMapper
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
    private val champMapper: ChampMapper
) : BaseViewModel() {
    private val headerViewHolderModelLiveData = MutableLiveData<HeaderViewHolderModel>()
    private val listItemRvLiveData = MutableLiveData<List<ItemRv>>()
    private var itemOriginHolderViewHolder = ItemHolderViewHolder(
        ItemHolderViewHolder.ClassOrOrigin(
            classOrOriginName = "",
            bonus = listOf(),
            content = ""
        ),
        listOf()
    )
    private var itemClassHolderViewHolder = ItemHolderViewHolder(
        ItemHolderViewHolder.ClassOrOrigin(
            classOrOriginName = "",
            bonus = listOf(),
            content = ""
        ),
        listOf()
    )
    private var listItemRv = mutableListOf<ItemRv>()

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
                Log.d("Phuc", "$it")
            }) {
                listItemRv.add(0, itemHeaderMapper.map(it))
                headerViewHolderModelLiveData.value = itemHeaderMapper.map(it)
                getOriginContent(it.origin, true)
                getClassContent((it.classs.split(","))[0], true)
                if((it.classs.split(",")).size>1){
                    getClassContent((it.classs.split(","))[1], true)
                }
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
                    itemOriginHolderViewHolder.copy(
                        classOrOrigin = ItemHolderViewHolder.ClassOrOrigin(
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
            updateListItemRv(
                itemOriginHolderViewHolder.copy(listChamp = champMapper.mapList(it.champs))
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
                    itemClassHolderViewHolder.copy(
                        classOrOrigin = ItemHolderViewHolder.ClassOrOrigin(
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
            updateListItemRv(
                itemClassHolderViewHolder.copy(
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
                for (team in teamRecommendForChampMapper.mapList(it.teamBuilders)) {
                    updateListItemRv(team)
                }
            }
        }


    private fun updateItemOriginHolderViewHolder(model: ItemHolderViewHolder) {
        itemOriginHolderViewHolder = model
    }

    private fun updateItemClassHolderViewHolder(model: ItemHolderViewHolder) {
        itemClassHolderViewHolder = model
    }

    fun getHeaderViewHolderModel(): LiveData<HeaderViewHolderModel> {
        return headerViewHolderModelLiveData
    }

    private fun updateListItemRv(item: ItemRv) {
        listItemRv.add(item)
        listItemRvLiveData.value = listItemRv
    }

    fun getListItemRvLiveData(): LiveData<List<ItemRv>> {
        return listItemRvLiveData
    }
}