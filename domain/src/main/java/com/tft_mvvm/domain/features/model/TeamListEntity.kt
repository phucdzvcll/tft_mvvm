package com.tft_mvvm.domain.features.model

data class TeamListEntity(val teams: List<Team>) {
    data class Team(
        val nameTeam: String,
        val idTeam: String,
        val listIdChampMain : List<String>,
        val listIdSuitable : List<String>,
        val listIdChampThreeStar :List<String>,
        val listIdChamp: List<String>
    )

}