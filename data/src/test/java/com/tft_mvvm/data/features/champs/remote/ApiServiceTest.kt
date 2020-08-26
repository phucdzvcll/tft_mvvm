package com.tft_mvvm.data.features.champs.remote

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.HttpURLConnection

class ApiServiceTest : BaseTest() {

    @Test
    fun `get champ list response`() {
        mockHttpResponse(HttpURLConnection.HTTP_OK, "champ.json", "/feeds/list/1A0SIKZRDM-poLdRGlc0Ov_a5gDsZyH_ojpzYX5gj85A/od6/public/values?alt=json")
        runBlocking {
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
            val actual = apiServer.getChampList()
            val expected = ChampListResponse(Feed(listOf(caitlyn, ezreal)))
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `get team list`() {
        mockHttpResponse(HttpURLConnection.HTTP_OK, "team.json", "/feeds/list/1A0SIKZRDM-poLdRGlc0Ov_a5gDsZyH_ojpzYX5gj85A/oofczet/public/values?alt=json")
        runBlocking {
            val teamS = Team(
                idTeam = GsxNameTeam("1"),
                nameTeam = GsxNameTeam("S"),
                listStar = GsxNameTeam("1,1,1,1,3,1,1,1"),
                idItemSuitable = GsxNameTeam("////7,3///"),
                listIdChamp = GsxNameTeam("3,5,7,9,11,12,16,40")
            )
            val teamC = Team(
                idTeam = GsxNameTeam("2"),
                nameTeam = GsxNameTeam("C"),
                listStar = GsxNameTeam("1,3,1,1,3,1,1,1"),
                idItemSuitable = GsxNameTeam("/5,8//6,12///"),
                listIdChamp = GsxNameTeam("3,5,7,9,11,12,16,40")
            )
            val actual = apiServer.getTeamList()
            val expected = TeamListResponse(FeedTeam(listOf(teamS, teamC)))
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `get item  list`() {
        mockHttpResponse(HttpURLConnection.HTTP_OK, "item.json", "/feeds/list/1A0SIKZRDM-poLdRGlc0Ov_a5gDsZyH_ojpzYX5gj85A/otafl6o/public/values?alt=json")
        runBlocking {
            val actual = apiServer.getItemListResponse()
            val timbang = Item(
                itemName = GsxNameItem("Tim Băng"),
                itemId = GsxNameItem("6"),
                itemAvatar = GsxNameItem("https://rerollcdn.com/items/GiantSlayer.png")
            )
            val daychuyenchuoctoi = Item(
                itemName = GsxNameItem("Dây Chuyền Chuộc Tội"),
                itemId = GsxNameItem("23"),
                itemAvatar = GsxNameItem("https://rerollcdn.com/items/Redemption.png")
            )
            val expected = ItemListResponse(FeedItem(listOf(timbang,daychuyenchuoctoi)))
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `get origin and class list`() {
        mockHttpResponse(HttpURLConnection.HTTP_OK, "class_origin.json", "/feeds/list/1A0SIKZRDM-poLdRGlc0Ov_a5gDsZyH_ojpzYX5gj85A/os462da/public/values?alt=json")
        runBlocking {
            val actual = apiServer.getClassAndOriginList()
            val vutru = ClassAndOrigin(
                classOrOriginName = GsxNameClassAndOrigin("Vũ Trụ"),
                content = GsxNameClassAndOrigin("Tất cả đồng minh được hồi máu theo phần trăm theo sát thương họ gây ra."),
                bonus = GsxNameClassAndOrigin("2: 15% Hồi máu theo sát thương,4: 30% Hồi máu theo sát thương,6: 60 Hồi máu theo sát thương%")
            )
            val phuthuy = ClassAndOrigin(
                classOrOriginName = GsxNameClassAndOrigin("Phù Thủy"),
                content = GsxNameClassAndOrigin("2: 20%,4: 40%,6: 80%"),
                bonus = GsxNameClassAndOrigin("Tất cả đồng minh nhận thêm Sức mạnh kỹ năng")
            )
            val gsxNameClassAndOriginActual = actual?.feedClassAndOrigin?.classAndOrigins?.get(0)?.classOrOriginName
            val gsxNameClassAndOriginExpected = GsxNameClassAndOrigin("Vũ Trụ")
            assertEquals(gsxNameClassAndOriginExpected, gsxNameClassAndOriginActual)

            val expected = ClassAndOriginListResponse(FeedClassAndOrigin(listOf(vutru,phuthuy)))
            assertEquals(expected, actual)
        }
    }
}