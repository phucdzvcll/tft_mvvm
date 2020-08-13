package com.tft_mvvm.app.features.details.viewmodel

//import com.tft_mvvm.app.mapper.ClassOrOriginMapper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.details.mapper.ItemMapper
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
    private val getListSuitableItemsUseCase: GetListSuitableItemsUseCase,
    private val getListChampsByClassUseCase: GetListChampsByClassUseCase,
    private val getListChampsByOriginUseCase: GetListChampsByOriginUseCase,
    private val getClassAndOriginContentUseCase: GetClassAndOriginContentUseCase,
    private val champMapper: ChampMapper,
    private val itemMapper: ItemMapper
) : BaseViewModel() {

    private val headerViewHolderModelLiveData = MutableLiveData<HeaderViewHolderModel>()
    private val listItemRvLiveData = MutableLiveData<List<ItemRv>>()
    var headerViewHolderModel = HeaderViewHolderModel(
        nameSkill = "",
        linkChampCover = "",
        activated = "",
        linkAvatarSkill = "",
        name = "",
        cost = "",
        listSuitableItem = listOf()
    )
    var itemOriginHolderViewHolder = ItemHolderViewHolder(
        ItemHolderViewHolder.ClassOrOrigin(
            classOrOriginName = "",
            bonus = listOf(),
            content = ""
        ),
        listOf()
    )
    var itemClassHolderViewHolder = ItemHolderViewHolder(
        ItemHolderViewHolder.ClassOrOrigin(
            classOrOriginName = "",
            bonus = listOf(),
            content = ""
        ),
        listOf()
    )
    var listItemRv = mutableListOf<ItemRv>()

    fun getChampById(id: String) = viewModelScope.launch(Dispatchers.Main) {
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
            updateHeaderViewHolderModel(
                headerViewHolderModel.copy(
                    nameSkill = it.skillName,
                    linkAvatarSkill = it.linkSkillAvatar,
                    activated = it.activated,
                    name = it.name,
                    cost = it.cost,
                    linkChampCover = it.linkChampCover
                )
            )
            getListItemSuitable(it.suitableItem, false)
            getOriginContent(it.origin, false)
            getClassContent(it.classs, false)
        }
    }

    private fun getListItemSuitable(
        listId: List<String>,
        isForceLoadData: Boolean
    ) =
        viewModelScope.launch(Dispatchers.Main) {
            val dbResult = withContext(Dispatchers.IO) {
                getListSuitableItemsUseCase.execute(
                    GetListSuitableItemsUseCase.GetListSuitableItemUseCaseParam(
                        isForceLoadData = isForceLoadData,
                        listId = listId
                    )
                )
            }
            dbResult.either({
                //TODO handel error
                Log.d("Phuc", "$it")
            }) {
                listItemRv.add(
                    0,
                    headerViewHolderModel.copy(
                        listSuitableItem = itemMapper.mapList(it.item)
                    )
                )
                listItemRvLiveData.value = listItemRv
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
                itemOriginHolderViewHolder.copy(
                    classOrOrigin = ItemHolderViewHolder.ClassOrOrigin(
                        classOrOriginName = it.classOrOriginName,
                        content = it.content,
                        bonus = it.bonus.split(",")
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
                itemOriginHolderViewHolder.copy(
                    listChamp = champMapper.mapList(it.champs)
                )
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
            }) {
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

    private fun updateHeaderViewHolderModel(model: HeaderViewHolderModel) {
        headerViewHolderModel = model
        headerViewHolderModelLiveData.value = headerViewHolderModel
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