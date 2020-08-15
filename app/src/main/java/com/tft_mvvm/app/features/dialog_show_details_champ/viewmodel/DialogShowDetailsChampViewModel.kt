package com.tft_mvvm.app.features.dialog_show_details_champ.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.dialog_show_details_champ.mapper.ChampDialogModelMapper
import com.tft_mvvm.app.features.dialog_show_details_champ.model.ChampDialogModel
import com.tft_mvvm.domain.features.usecase.GetChampByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DialogShowDetailsChampViewModel(
    private val getChampByIdUseCase: GetChampByIdUseCase,
    private val champDialogModelMapper: ChampDialogModelMapper
) : BaseViewModel() {

    private val champByDialogLiveData  = MutableLiveData<ChampDialogModel>()

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
            champByDialogLiveData.value = champDialogModelMapper.map(it)
        }
    }

    fun getChampByDialogLiveData():LiveData<ChampDialogModel>{
        return champByDialogLiveData
    }
}