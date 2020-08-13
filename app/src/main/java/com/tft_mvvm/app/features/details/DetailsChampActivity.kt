package com.tft_mvvm.app.features.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tft_mvvm.app.features.details.adapter.AdapterShowDetailsChamp
import com.tft_mvvm.app.features.details.model.HeaderViewHolderModel
import com.tft_mvvm.app.features.details.viewmodel.DetailsViewModel
import com.tft_mvvm.app.ui.OnItemClickListener

import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.activity_details_champ.*
import org.koin.android.viewmodel.ext.android.viewModel


class DetailsChampActivity : AppCompatActivity(), OnItemClickListener {
    private val detailsViewModel: DetailsViewModel by viewModel()
    private var adapterShowDetailsChamp: AdapterShowDetailsChamp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_champ)
        setupUi()
        observerViewModel()

        getChamp(intent)?.let {
            detailsViewModel.getChampById(it)

        }
    }

    private fun observerViewModel() {
        detailsViewModel.getHeaderViewHolderModel().observe(this, Observer {
            toolbar_title.text = it.name
            champ_cost.text = it.cost
        })
        detailsViewModel.getListItemRvLiveData().observe(this, Observer {
            adapterShowDetailsChamp?.addData(it)
        })
    }

    private fun setupUi() {
        rv_show_details_champ?.layoutManager = LinearLayoutManager(this)
        adapterShowDetailsChamp = AdapterShowDetailsChamp(arrayListOf(), this)
        rv_show_details_champ?.adapter = adapterShowDetailsChamp
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
        //val listIdItem: List<Item>,
        val cost: String
    )

}


