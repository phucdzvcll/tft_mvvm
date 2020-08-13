package com.tft_mvvm.app.features.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel

import com.tft_mvvm.app.mapper.ChampMapper
//import com.tft_mvvm.app.mapper.ClassOrOriginMapper
import com.tft_mvvm.app.features.details.DetailsChampActivity

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
    //private val detailsChampMapper: DetailsChampMapper,
    //private val itemMapper: ItemMapper,
    private val champByIdUseCase: GetChampByIdUseCase,
    //private val classOrOriginMapper: ClassOrOriginMapper,
    private val champListMapper: ChampMapper
) : BaseViewModel() {
//    private val champByOriginLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
//    private val champByClassLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
//    private val champAfterUpdateLiveData: MutableLiveData<Champ> = MutableLiveData()
//    private val classContentLiveData: MutableLiveData<ItemDetailsViewHolderModel.ClassOrOrigin> =
//        MutableLiveData()
//    private val originContentLiveData: MutableLiveData<ItemDetailsViewHolderModel.ClassOrOrigin> =
//        MutableLiveData()
//    private val listItemSuitableLiveData: MutableLiveData<List<Item>> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private val champByIdLiveData: MutableLiveData<DetailsChampActivity.DetailsChamp> =
        MutableLiveData()

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
               // champByOriginLiveData.value = champs
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
               // champByClassLiveData.value = champs
            }
        }

    fun getClassAndOriginContent(
        isForceLoadData: Boolean,
        nameClassOrOrigin: String,
        type: String
    ) =
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
                   // "origin" -> originContentLiveData.value =
                     //   classOrOriginMapper.map(classOrOriginEntity)
                  //  "class" -> classContentLiveData.value =
                      //  classOrOriginMapper.map(classOrOriginEntity)
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
              //  champAfterUpdateLiveData.value = champListMapper.map(it)
                isLoadingLiveData.value = false
            }
        }

    fun getChampById(id: String) = viewModelScope.launch(Dispatchers.Main) {
        val dbResult = withContext(Dispatchers.IO) {
            champByIdUseCase.execute(
                GetChampByIdUseCase.GetChampByIdUseCaseParam(
                    id
                )
            )
        }
        dbResult.either({
            //TODO error handle
        }) {
            //champByIdLiveData.value = detailsChampMapper.map(it)
        }
    }

    fun getChampByIdLiveData(): LiveData<DetailsChampActivity.DetailsChamp> {
        return champByIdLiveData
    }

//    fun getChampAfterUpdateLiveData(): LiveData<Champ> {
//        return champAfterUpdateLiveData
//    }
//
//    fun isRefresh(): LiveData<Boolean> {
//        return isLoadingLiveData
//    }
//
//    fun getListItemSuitableLiveData(): LiveData<List<Item>> {
//        return listItemSuitableLiveData
//    }
//
//    fun getClassContentLiveData(): LiveData<ItemDetailsViewHolderModel.ClassOrOrigin> {
//        return classContentLiveData
//    }
//
//    fun getOriginContentLiveData(): LiveData<ItemDetailsViewHolderModel.ClassOrOrigin> {
//        return originContentLiveData
//    }
//
//    fun getChampsByOriginLiveData(): LiveData<List<Champ>> {
//        return champByOriginLiveData
//    }
//
//    fun getChampsByClassLiveData(): LiveData<List<Champ>> {
//        return champByClassLiveData
//    }

}