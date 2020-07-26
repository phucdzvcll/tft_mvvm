package com.example.presentation.features.champs.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.presentation.base.BaseViewModel
import com.example.presentation.features.champs.model.model.Champ
import com.example.presentation.mapper.ChampMapper
import com.tft_mvvm.domain.features.champs.usecase.GetChampsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(

    private val champsUseCase: GetChampsUseCase,
    private val champListMapper: ChampMapper
) : BaseViewModel() {
    private val champLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val champByOriginLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val champByClassLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getChamps(name: String, linkImg: String, coat: String, origin:String, classs:String, id:String,skillName :String, linkSkillAvatar:String, activated:String) =
        viewModelScope.launch(Dispatchers.Main) {
            isLoadingLiveData.value = true
            val champResult = withContext(Dispatchers.IO) {
                champsUseCase.execute(
                    GetChampsUseCase.GetChampsUseCaseParam(
                        name = name,
                        linkImg = linkImg,
                        coat = coat,
                        origin = origin,
                        classs = classs,
                        id = id,
                        skillName = skillName,
                        activated = activated,
                        linkSkillAvatar = linkSkillAvatar
                    )
                )
            }
            champLiveData.value = champListMapper.mapList(champResult.champs)
            isLoadingLiveData.value = false
        }

    fun getChampByOrigin(name: String, linkImg: String, coat: String, origin:String, classs:String,id:String,skillName :String, linkSkillAvatar:String, activated:String){
        viewModelScope.launch(Dispatchers.Main) {
            isLoadingLiveData.value = true
            val champResult = withContext(Dispatchers.IO) {
                champsUseCase.execute(
                    GetChampsUseCase.GetChampsUseCaseParam(
                        name = name,
                        linkImg = linkImg,
                        coat = coat,
                        origin = origin,
                        classs = classs,
                        id = id,
                        skillName = skillName,
                        activated = activated,
                        linkSkillAvatar = linkSkillAvatar
                    )
                )
            }

            val champs = champListMapper.mapList(champResult.champs)
            var resultByOrigin : MutableList<Champ> = mutableListOf()
            if(origin.isNotEmpty()){
                when(origin){
                    "Không Tặc" -> resultByOrigin.addAll(champs.filter { champ -> champ.origin==origin||champ.name=="Gangplank" })
                    else -> resultByOrigin.addAll(champs.filter { champ -> champ.origin==origin})
                }
            }
            champByOriginLiveData.value=resultByOrigin
        }
    }
    fun getChampByClasss(name: String, linkImg: String, coat: String, origin:String, classs:String,id:String,skillName :String, linkSkillAvatar:String, activated:String){
        viewModelScope.launch(Dispatchers.Main) {

            val champResult = withContext(Dispatchers.IO) {
                champsUseCase.execute(
                    GetChampsUseCase.GetChampsUseCaseParam(
                        name = name,
                        linkImg = linkImg,
                        coat = coat,
                        origin = origin,
                        classs = classs,
                        id = id,
                        skillName = skillName,
                        activated = activated,
                        linkSkillAvatar = linkSkillAvatar
                    )
                )
            }
            val champs = champListMapper.mapList(champResult.champs)
            var resultByClasss : MutableList<Champ> = mutableListOf()
            if(classs.isNotEmpty()){
                when(classs){
                    "Ma Tặc" -> resultByClasss.addAll(champs.filter { champ -> champ.classs==classs||champ.name=="Irelia" })
                    else -> resultByClasss.addAll(champs.filter { champ -> champ.classs==classs})
                }
            }
            champByClassLiveData.value=resultByClasss
        }
    }

    fun getChampsLiveData(): LiveData<List<Champ>> {
        return champLiveData
    }
    fun getChampsByOriginLiveData():LiveData<List<Champ>>{
        return champByOriginLiveData
    }
    fun getChampsByClassLiveData():LiveData<List<Champ>>{
        return champByClassLiveData
    }
    fun isRefresh(): LiveData<Boolean> {
        return isLoadingLiveData
    }
}