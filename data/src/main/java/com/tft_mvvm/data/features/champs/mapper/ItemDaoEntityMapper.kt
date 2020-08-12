package com.tft_mvvm.data.features.champs.mapper

import com.example.common_jvm.extension.nullable.defaultEmpty
import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.data.features.champs.model.ItemListResponse
import com.tft_mvvm.data.local.model.ItemListDBO

class ItemDaoEntityMapper : Mapper<ItemListResponse?, ItemListDBO>() {
    override fun map(input: ItemListResponse?): ItemListDBO {
        val itemDbo = input?.feedItem?.items?.filterNotNull().defaultEmpty().map { item ->
            ItemListDBO.ItemDBO(
                itemId = item.itemId?.value.defaultEmpty(),
                itemAvatar = item.itemAvatar?.value.defaultEmpty(),
                nameItem = item.itemName?.value.defaultEmpty()
            )
        }
        return ItemListDBO(itemDbo)
    }
}