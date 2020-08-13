package com.tft_mvvm.domain.features.model

data class TeamListEntity(val teams: List<Team>) {
    data class Team(
        val name: String,
        val id: String,
        val listId: List<String>
    )

}