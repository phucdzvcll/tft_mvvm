package com.tft_mvvm.app.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tft_mvvm.app.features.champ.model.Champ
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

        val list = ArrayList<ItemRv>()
        getChamp(intent)?.let { champ ->
            setupUI(champ)
            observerViewModel(true, champ)
            getData(champ, list)
        }
    }

    private fun getData(champ: Champ, list: ArrayList<ItemRv>) {
        detailsViewModel.getListItemSuitableLiveData().observe(this, Observer {
            val headerViewHolderModel = HeaderViewHolderModel(
                nameSkill = champ.skillName,
                activated = champ.activated,
                linkAvatarSkill = champ.linkSkilAvatar,
                linkChampCover = champ.linkChampCover,
                listSuitableItem = it
            )
            list.add(0, headerViewHolderModel)
            adapterShowDetailsChamp?.addData(list.toList())
        })
        detailsViewModel.getChampsByOriginLiveData().observe(this, Observer { listChamp ->
            detailsViewModel.getOriginContentLiveData().observe(this, Observer { classOrOrigin ->
                val itemDetailsViewHolderModel = ItemDetailsViewHolderModel(
                    classOrOrigin = classOrOrigin,
                    listChamp = listChamp
                )
                list.add(itemDetailsViewHolderModel)
                adapterShowDetailsChamp?.addData(list.toList())
            })
        })
        detailsViewModel.getChampsByClassLiveData().observe(this, Observer { listChamp ->
            detailsViewModel.getClassContentLiveData().observe(this, Observer { classOrOrigin ->
                val itemDetailsViewHolderModel = ItemDetailsViewHolderModel(
                    classOrOrigin = classOrOrigin,
                    listChamp = listChamp
                )
                list.add(itemDetailsViewHolderModel)
                adapterShowDetailsChamp?.addData(list.toList())
            })
        })
    }

    private fun setupUI(champ: Champ) {
        adapterShowDetailsChamp = AdapterShowDetailsChamp(arrayListOf(), this)
        rv_show_details_champ.layoutManager = LinearLayoutManager(this)
        rv_show_details_champ.adapter = adapterShowDetailsChamp
        item_btn_back.setOnClickListener { finish() }
        champ_cost.text = champ.cost
        toolbar_title.text = champ.name
        SwipeRefreshLayoutDetailsActivity.setOnRefreshListener {
            detailsViewModel.getChampAfterUpdateLiveData().observe(this, Observer {

            })
        }
        detailsViewModel.isRefresh().observe(this, Observer {
            SwipeRefreshLayoutDetailsActivity.isRefreshing = it
        })
    }

    private fun observerViewModel(isForceLoadData: Boolean, champ: Champ) {
        detailsViewModel.getOriginContent(isForceLoadData, champ.origin, "origin")
        detailsViewModel.getOriginContent(isForceLoadData, champ.classs, "class")
        detailsViewModel.getListItemSuitable(isForceLoadData, champ.suitableItem)
        detailsViewModel.getChampsByClass(champ.classs)
        detailsViewModel.getChampsByOrigin(champ.origin)
        detailsViewModel.updateChamp(champ.id)
    }

    companion object {
        private const val CHAMP_EXTRA = "champ_extra"

        fun newIntent(context: Context, champ: Champ): Intent {
            val intent = Intent(context, DetailsChampActivity::class.java)
            intent.putExtra(CHAMP_EXTRA, champ)
            return intent
        }

        fun getChamp(intent: Intent): Champ? {
            return intent.getSerializableExtra(CHAMP_EXTRA) as? Champ
        }
    }

    override fun onClickListener(champ: Champ) {
        val dialog = com.tft_mvvm.app.ui.Dialog(this, champ)
        dialog.show()
    }

}


