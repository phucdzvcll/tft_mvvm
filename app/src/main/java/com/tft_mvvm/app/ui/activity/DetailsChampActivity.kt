package com.tft_mvvm.app.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.features.champ.viewmodel.DetailsViewModel
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.app.ui.adapter.AdapterShowBonusOfClassOrOrigin
import com.tft_mvvm.app.ui.adapter.AdapterShowByOriginAndClass
import com.tft_mvvm.champ.R
import com.tft_mvvm.champ.databinding.ActivityDetailsChampBinding
import kotlinx.android.synthetic.main.activity_details_champ.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class DetailsChampActivity : AppCompatActivity(), OnItemClickListener {
    private var biding: ActivityDetailsChampBinding? = null
    private val detailsViewModel: DetailsViewModel by viewModel()
    private var adapterShowByOrigin: AdapterShowByOriginAndClass? = null
    private var adapterShowByClass: AdapterShowByOriginAndClass? = null
    private var adapterShowBonusOfOrigin: AdapterShowBonusOfClassOrOrigin? = null
    private var adapterShowBonusOfClass: AdapterShowBonusOfClassOrOrigin? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biding = DataBindingUtil.setContentView(this, R.layout.activity_details_champ)

        getChamp(intent)?.let { champ ->
            setupUI(champ)

        }

        detailsViewModel.getChampAfterUpdateLiveData().observe(this, Observer {
            getListItemSuitable(false, it)
            setupUI(it)
        })
        detailsViewModel.isRefresh()
            .observe(this, Observer { it ->
                SwipeRefreshLayoutDetailsActivity.isRefreshing = it
            })
    }

    private fun setupUI(champ: Champ) {
        biding?.toolbarTitle?.text = champ.name
        biding?.itemBtnBack?.setOnClickListener { finish() }
        biding?.champCost?.text = champ.cost
        biding?.origin?.text = champ.origin.toUpperCase(locale = Locale.ROOT)
        biding?.classs?.text = champ.classs.toUpperCase(locale = Locale.ROOT)
        biding?.skillAvatar?.let {
            Glide.with(this)
                .load(champ.linkSkilAvatar)
                .into(it)
        }
        biding?.champCover?.let {
            Glide.with(this)
                .load(champ.linkChampCover)
                .into(it)
        }
        skill_name.text = champ.skillName
        activated.text = champ.activated
        biding?.rvOrigin?.layoutManager = GridLayoutManager(this, 6)
        adapterShowByOrigin = AdapterShowByOriginAndClass(arrayListOf(), this)
        biding?.rvOrigin?.adapter = adapterShowByOrigin

        biding?.rvClasss?.layoutManager = GridLayoutManager(this, 6)
        adapterShowByClass = AdapterShowByOriginAndClass(arrayListOf(), this)
        biding?.rvClasss?.adapter = adapterShowByClass

        biding?.rvOriginBonus?.layoutManager = LinearLayoutManager(this)
        adapterShowBonusOfOrigin = AdapterShowBonusOfClassOrOrigin(arrayListOf())
        biding?.rvOriginBonus?.adapter = adapterShowBonusOfOrigin

        biding?.rvClassBonus?.layoutManager = LinearLayoutManager(this)
        adapterShowBonusOfClass = AdapterShowBonusOfClassOrOrigin(arrayListOf())
        biding?.rvClassBonus?.adapter = adapterShowBonusOfClass

        SwipeRefreshLayoutDetailsActivity.setOnRefreshListener {
            detailsViewModel.updateChamp(champ.id)
        }

        getChampByOrigin(champ)
        getChampByClass(champ)
        getClassAndOriginContent(false, champ)
        getListItemSuitable(true, champ)
    }

    private fun getChampByOrigin(champ: Champ) {
        detailsViewModel.getChampsByOrigin(champ.origin)
        detailsViewModel.getChampsByOriginLiveData()
            .observe(this, Observer {
                adapterShowByOrigin?.addData(it)
            })

    }

    private fun getChampByClass(champ: Champ) {
        detailsViewModel.getChampsByClass(champ.classs)
        detailsViewModel.getChampsByClassLiveData()
            .observe(this, Observer {
                adapterShowByClass?.addData(it)
            })
    }

    private fun getClassAndOriginContent(isForceLoadData: Boolean, champ: Champ) {
        detailsViewModel.getClassContentLiveData().observe(this, Observer {
            adapterShowBonusOfClass?.addData(it.bonus)
            biding?.classContent?.text = it.content
        })
        detailsViewModel.getOriginContentLiveData().observe(this, Observer {
            adapterShowBonusOfOrigin?.addData(it.bonus)
            biding?.originContent?.text = it.content
        })
        detailsViewModel.getOriginContent(isForceLoadData, champ.origin, "origin")
        detailsViewModel.getOriginContent(false, champ.classs, "class")
    }

    private fun getListItemSuitable(isForceLoadData: Boolean, champ: Champ) {
        detailsViewModel.getListItemSuitableLiveData().observe(this, Observer {
            Glide.with(suitable_item_img_1.context)
                .load(it[0].itemAvatar)
                .into(suitable_item_img_1)
            Glide.with(suitable_item_img_2.context)
                .load(it[1].itemAvatar)
                .into(suitable_item_img_2)
            Glide.with(suitable_item_img_3.context)
                .load(it[2].itemAvatar)
                .into(suitable_item_img_3)
        })
        detailsViewModel.getListItemSuitable(isForceLoadData, champ.suitableItem)
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


