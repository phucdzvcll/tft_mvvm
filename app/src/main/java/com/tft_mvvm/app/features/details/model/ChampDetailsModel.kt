package com.tft_mvvm.app.features.details.model

data class ChampDetailsModel(
    val headerModel: HeaderModel?,
    val listItem: List<ClassAndOriginContent>,
    val listTeamRecommend: List<TeamRecommend>
) {
    data class HeaderModel(
        val nameSkill: String,
        val activated: String,
        val linkAvatarSkill: String,
        val linkChampCover: String,
        val name: String,
        val cost: String,
        val listSuitableItem: List<Item>
    )

    data class ClassAndOriginContent(
        val listChamp: List<Champ>,
        val classOrOriginName: String,
        val bonus: List<String>,
        val content: String
    )
    data class TeamRecommend(
        val name: String,
        val listChamp: List<Champ>
    )
    data class Champ(
        val id: String,
        val name: String,
        val imgUrl: String,
        val cost: String,
        val threeStar: String,
        val itemSuitable: List<Item>
    )

    data class Item(
        val id: String,
        val itemName: String,
        val itemAvatar: String
    )
}