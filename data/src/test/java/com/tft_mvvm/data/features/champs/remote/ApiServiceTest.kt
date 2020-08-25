package com.tft_mvvm.data.features.champs.remote

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import org.koin.ext.getOrCreateScope
import retrofit2.http.GET
import java.net.HttpURLConnection

class ApiServiceTest : BaseTest() {

    @Test
    fun `get champ list response`() {
        mockHttpResponse(mockServer, "search_champ.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val actual = apiServer.getChampList()
            val caitlyn = Champ(
                name = GsxName("Caitlyn"),
                id = GsxName("1"),
                linkImg = GsxName("https://rerollcdn.com/characters/Skin/3.5/Caitlyn.png"),
                linkSkillAvatar = GsxName("https://rerollcdn.com/abilities/3.5/caitlyn-ace-in-the-hole.png"),
                originAndClassName = GsxName("Thời Không,Xạ Thủ"),
                cost = GsxName("1"),
                activated = GsxName("Caitlyn nhắm vào kẻ thù xa nhất, gây sát thương phép lên kẻ thù này. Sát thương: 700/1000/1800."),
                linkChampCover = GsxName("https://cdn.lolchess.gg/images/tft/champions/set3/splash/tft3_Caitlyn.jpg"),
                skillName = GsxName("Bách Phát Bách Trúng"),
                rankChamp = GsxName("B"),
                suitableItem = GsxName("9,24,31")
            )
            val ezreal = Champ(
                name = GsxName("Ezreal"),
                id = GsxName("2"),
                linkImg = GsxName("https://rerollcdn.com/characters/Skin/3.5/Ezreal.png"),
                linkSkillAvatar = GsxName("https://rerollcdn.com/abilities/3.5/ezreal-e.m.p..png"),
                originAndClassName = GsxName("Pháo Thủ,Thời Không"),
                cost = GsxName("3"),
                activated = GsxName("Ezreal bắn ra một quả cầu dính vào kẻ thù ngẫu nhiên. gây 250 / 350 / 700 lên kẻ thù xung quanh mục tiêu đồng thời khiến kẻ thù phải mất thêm 40% năng lượng để sử dụng kỹ năng."),
                linkChampCover = GsxName("https://cdn.lolchess.gg/images/tft/champions/set3/splash/tft3_Ezreal.jpg"),
                skillName = GsxName("Tinh Hoa Tuôn Chảy"),
                rankChamp = GsxName("B"),
                suitableItem = GsxName("2,17,18")
            )
            val expected = ChampListResponse(Feed(mutableListOf(caitlyn, ezreal)))
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `get champ list response 403`() {
        runBlocking {
            val url = "feeds/list/1A0SIKZRDM-poLdRGlc0Ov_a5gDsZyH_ojpzYX5gj85A/od6/public/values?alt=json"
            mockServer.url("/$url")
        }
    }
}