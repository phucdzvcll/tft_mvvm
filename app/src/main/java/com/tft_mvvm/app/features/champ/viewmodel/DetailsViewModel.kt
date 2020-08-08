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
import com.tft_mvvm.domain.features.champs.usecase.GetChampsByClassUseCase
import com.tft_mvvm.domain.features.champs.usecase.GetChampsByOriginUseCase
import com.tft_mvvm.domain.features.champs.usecase.GetClassAndOriginContentUseCase
import com.tft_mvvm.domain.features.champs.usecase.GetListSuitableItemUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val champsByOriginUseCase: GetChampsByOriginUseCase,
    private val champsByClassUseCase: GetChampsByClassUseCase,
    private val classAndOriginUseCase: GetClassAndOriginContentUseCase,
    private val itemListSuitableItemUseCase: GetListSuitableItemUseCase,
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
                champsByOriginUseCase.execute(
                    GetChampsByOriginUseCase.GetChampsByOriginUseCaseParam(
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
                champsByClassUseCase.execute(
                    GetChampsByClassUseCase.GetChampsByClassUseCaseParam(
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
                itemListSuitableItemUseCase.execute(
                    GetListSuitableItemUseCase.GetListSuitableItemUseCaseParam(
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