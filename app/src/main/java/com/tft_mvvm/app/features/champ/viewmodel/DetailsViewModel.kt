package com.tft_mvvm.app.features.champ.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.features.champ.model.ClassOrOrigin
import com.tft_mvvm.app.features.champ.model.Item
import com.tft_mvvm.app.mapper.ChampMapper
import com.tft_mvvm.app.mapper.ClassOrOriginMapper
import com.tft_mvvm.app.mapper.ItemMapper
import com.tft_mvvm.domain.features.model.ChampListEntity
import com.tft_mvvm.domain.features.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val listChampsByOriginUseCase: GetListChampsByOriginUseCase,
    private val listChampsByClassUseCase: GetListChampsByClassUseCase,
    private val classAndOriginUseCase: GetClassAndOriginContentUseCase,
    private val itemListSuitableItemsUseCase: GetListSuitableItemsUseCase,
    private val updateChampUseCase: UpdateChampUseCase,
    private val itemMapper: ItemMapper,
    private val classOrOriginMapper: ClassOrOriginMapper,
    private val champListMapper: ChampMapper
) : BaseViewModel() {
    private val champByOriginLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val champByClassLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val champAfterUpdateLiveData: MutableLiveData<Champ> = MutableLiveData()
    private val classContentLiveData: MutableLiveData<ClassOrOrigin> = MutableLiveData()
    private val originContentLiveData: MutableLiveData<ClassOrOrigin> = MutableLiveData()
    private val listItemSuitableLiveData: MutableLiveData<List<Item>> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
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
                val listItem = itemMapper.mapList(it.item)
                if (listItem.size == 3) {
                    listItemSuitableLiveData.value = listItem
                }
            }
        }

    fun updateChamp(id: String) =
        viewModelScope.launch(Dispatchers.Main) {
            isLoadingLiveData.value = true
            val dbResult = withContext(Dispatchers.IO) {
                updateChampUseCase.execute(
                    UpdateChampUseCase.UpdateChampUseCaseParam(
                        id
                    )
                )
            }
            dbResult.either({
                //TODO error handle
            }) {
                champAfterUpdateLiveData.value = champListMapper.map(it)
                isLoadingLiveData.value = false
            }
        }

    fun getChampAfterUpdateLiveData():LiveData<Champ>{
        return champAfterUpdateLiveData
    }

    fun isRefresh(): LiveData<Boolean> {
        return isLoadingLiveData
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