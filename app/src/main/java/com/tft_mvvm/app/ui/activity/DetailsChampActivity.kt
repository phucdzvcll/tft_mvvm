package com.tft_mvvm.app.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tft_mvvm.app.features.champ.model.Item
import com.tft_mvvm.app.features.champ.viewmodel.DetailsViewModel
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.app.ui.adapter.AdapterShowDetailsChamp
import com.tft_mvvm.app.ui.adapter.HeaderViewHolderModel
import com.tft_mvvm.app.ui.adapter.ItemDetailsViewHolderModel
import com.tft_mvvm.app.ui.adapter.ItemRv
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.activity_details_champ.*
import org.koin.android.viewmodel.ext.android.viewModel


class DetailsChampActivity : AppCompatActivity(), OnItemClickListener {
    private val detailsViewModel: DetailsViewModel by viewModel()
    private var adapterShowDetailsChamp: AdapterShowDetailsChamp? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_champ)
        observerGetData()
        getChamp(intent)?.let {
            observerViewModelGetChampById(it)
        }
        observerGetData()
    }

    private fun observerViewModelGetChampById(id: String) {
        detailsViewModel.getChampById(id)
    }

    private fun observerGetData() {
        detailsViewModel.getChampByIdLiveData().observe(this, Observer {
            detailsViewModel.getChampsByClass(it.classs)
            detailsViewModel.getChampsByOrigin(it.origin)
            detailsViewModel.getClassAndOriginContent(true, it.origin, "origin")
            detailsViewModel.getClassAndOriginContent(false, it.classs, "class")
        })
    }

    private fun getData(detailsChamp: DetailsChamp) {
        val listItemRv = ArrayList<ItemRv>()
            setupUi(detailsChamp = detailsChamp)
            detailsViewModel.getListItemSuitableLiveData().observe(this, Observer {
                val headerViewHolderModel = HeaderViewHolderModel(
                    nameSkill = detailsChamp.skillName,
                    linkAvatarSkill = detailsChamp.linkAvatarSkill,
                    activated = detailsChamp.activated,
                    linkChampCover = detailsChamp.linkCover,
                    listSuitableItem = it
                )
                listItemRv.add(0,headerViewHolderModel)
                adapterShowDetailsChamp?.addData(listItemRv.toList())
            })

            detailsViewModel.getChampsByClassLiveData().observe(this, Observer { listChamp ->
                detailsViewModel.getClassContentLiveData().observe(this, Observer { classOrOrigin ->
                    val itemDetailsViewHolderModel = ItemDetailsViewHolderModel(
                        classOrOrigin = classOrOrigin,
                        listChamp = listChamp
                    )
                    listItemRv.add(itemDetailsViewHolderModel)
                    adapterShowDetailsChamp?.addData(listItemRv.toList())
                })
            })
    }

    private fun setupUi(detailsChamp: DetailsChamp) {
        rv_show_details_champ?.layoutManager = LinearLayoutManager(this)
        adapterShowDetailsChamp = AdapterShowDetailsChamp(arrayListOf(), this)
        rv_show_details_champ?.adapter = adapterShowDetailsChamp

        toolbar_title.text = detailsChamp.name
        champ_cost.text = detailsChamp.cost
    }

    companion object {
        private const val CHAMP_EXTRA = "id_extra"

        fun newIntent(context: Context, id: String): Intent {
            val intent = Intent(context, DetailsChampActivity::class.java)
            intent.putExtra(CHAMP_EXTRA, id)
            return intent
        }

        fun getChamp(intent: Intent): String? {
            return intent.getStringExtra(CHAMP_EXTRA)
        }
    }

    override fun onClickListener(id: String) {
    }

    data class DetailsChamp(
        val name: String,
        val id: String,
        val origin: String,
        val classs: String,
        val activated: String,
        val skillName: String,
        val linkCover: String,
        val linkAvatarSkill: String,
        val listIdItem: List<Item>,
        val cost: String
    )

}


