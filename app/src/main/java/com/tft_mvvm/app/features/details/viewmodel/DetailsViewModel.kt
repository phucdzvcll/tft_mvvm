package com.tft_mvvm.app.features.details.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel

import com.tft_mvvm.app.mapper.ChampMapper
//import com.tft_mvvm.app.mapper.ClassOrOriginMapper
import com.tft_mvvm.app.features.details.DetailsChampActivity
import com.tft_mvvm.app.features.details.mapper.ItemMapper
import com.tft_mvvm.app.features.details.model.HeaderViewHolderModel
import com.tft_mvvm.app.features.details.model.ItemRv

import com.tft_mvvm.domain.features.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val getChampByIdUseCase: GetChampByIdUseCase,
    private val getListSuitableItemsUseCase: GetListSuitableItemsUseCase,
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
            Log.d("Phuc", "$it")
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
                Log.d("Phuc","$it")
            }) {
                listItemRv.add(
                    headerViewHolderModel.copy(
                        listSuitableItem = itemMapper.mapList(it.item)
                    )
                )
                listItemRvLiveData.value = listItemRv.toList()
            }
        }

    private fun updateHeaderViewHolderModel(model: HeaderViewHolderModel) {
        headerViewHolderModel = model
        headerViewHolderModelLiveData.value = headerViewHolderModel
    }

    fun getHeaderViewHolderModel(): LiveData<HeaderViewHolderModel> {
        return headerViewHolderModelLiveData
    }

    fun getListItemRvLiveData(): LiveData<List<ItemRv>> {
        return listItemRvLiveData
    }
}