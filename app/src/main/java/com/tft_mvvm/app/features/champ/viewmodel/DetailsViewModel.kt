package com.tft_mvvm.app.features.champ.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.features.champ.model.ClassOrOrigin
import com.tft_mvvm.app.features.champ.model.Item
import com.tft_mvvm.app.mapper.ChampMapper
import com.tft_mvvm.app.mapper.ClassOrOriginMapper
import com.tft_mvvm.app.mapper.ItemMapper
import com.tft_mvvm.domain.features.usecase.GetListChampsByClassUseCase
import com.tft_mvvm.domain.features.usecase.GetListChampsByOriginUseCase
import com.tft_mvvm.domain.features.usecase.GetClassAndOriginContentUseCase
import com.tft_mvvm.domain.features.usecase.GetListSuitableItemsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val listChampsByOriginUseCase: GetListChampsByOriginUseCase,
    private val listChampsByClassUseCase: GetListChampsByClassUseCase,
    private val classAndOriginUseCase: GetClassAndOriginContentUseCase,
    private val itemListSuitableItemsUseCase: GetListSuitableItemsUseCase,
    private val itemMapper: ItemMapper,
    private val classOrOriginMapper: ClassOrOriginMapper,
    private val champListMapper: ChampMapper
) : BaseViewModel() {
    private val champByOriginLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val champByClassLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val classContentLiveData: MutableLiveData<ClassOrOrigin> = MutableLiveData()
    private val originContentLiveData: MutableLiveData<ClassOrOrigin> = MutableLiveData()
    private val listItemSuitableLiveData: MutableLiveData<List<Item>> = MutableLiveData()
    fun getChampsByOrigin(origin: String) =
        viewModelScope.launch(Dispatchers.Main) {
            val champResult = withContext(Dispatchers.IO) {
                listChampsByOriginUseCase.execute(
                    GetListChampsByOriginUseCase.GetChampsByOriginUseCaseParam(
                        origin = origin
                    )
                )
            }
            champResult.either(
                {
                    //TODO error handle
                })
            {
                val champs = champListMapper.mapList(it.champs)
                champByOriginLiveData.value = champs
            }
        }

    fun getChampsByClass(nameClassOrOrigin: String) =
        viewModelScope.launch(Dispatchers.Main) {
            val champResult = withContext(Dispatchers.IO) {
                listChampsByClassUseCase.execute(
                    GetListChampsByClassUseCase.GetChampsByClassUseCaseParam(
                        classs = nameClassOrOrigin
                    )
                )
            }
            champResult.either(
                {
                    //TODO error handle
                })
            {
                val champs = champListMapper.mapList(it.champs)
                champByClassLiveData.value = champs
            }
        }

    fun getOriginContent(isForceLoadData: Boolean, nameClassOrOrigin: String, type: String) =
        viewModelScope.launch(Dispatchers.Main) {
            val dbResult = withContext(Dispatchers.IO) {
                classAndOriginUseCase.execute(
                    GetClassAndOriginContentUseCase.GetClassAnOriginContentParam(
                        isForceLoadData,
                        nameClassOrOrigin
                    )
                )
            }
            dbResult.either({
                //TODO error handle
            })
            { classOrOriginEntity ->
                when (type) {
                    "origin" -> originContentLiveData.value =
                        classOrOriginMapper.map(classOrOriginEntity)
                    "class" -> classContentLiveData.value =
                        classOrOriginMapper.map(classOrOriginEntity)
                }
            }
        }

    fun getListItemSuitable(isForceLoadData: Boolean, listId: String) =
        viewModelScope.launch(Dispatchers.Main) {
            val dbResult = withContext(Dispatchers.IO) {
                itemListSuitableItemsUseCase.execute(
                    GetListSuitableItemsUseCase.GetListSuitableItemUseCaseParam(
                        isForceLoadData,
                        listId
                    )
                )
            }
            dbResult.either({
                //TODO error handle
            })
            {
                val listItem = itemMapper.mapList(it.iteam)
                if (listItem.size == 3) {
                    listItemSuitableLiveData.value = listItem
                }
            }
        }

    fun getListItemSuitableLiveData(): LiveData<List<Item>> {
        return listItemSuitableLiveData
    }

    fun getClassContentLiveData(): LiveData<ClassOrOrigin> {
        return classContentLiveData
    }

    fun getOriginContentLiveData(): LiveData<ClassOrOrigin> {
        return originContentLiveData
    }

    fun getChampsByOriginLiveData(): LiveData<List<Champ>> {
        return champByOriginLiveData
    }

    fun getChampsByClassLiveData(): LiveData<List<Champ>> {
        return champByClassLiveData
    }

}