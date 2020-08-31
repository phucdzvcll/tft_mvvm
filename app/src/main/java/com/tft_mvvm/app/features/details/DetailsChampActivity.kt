package com.tft_mvvm.app.features.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common_jvm.extension.nullable.defaultEmpty
import com.tft_mvvm.app.base.OnItemClickListener
import com.tft_mvvm.app.features.details.adapter.AdapterShowDetailsChamp
import com.tft_mvvm.app.features.details.viewmodel.DetailsViewModel
import com.tft_mvvm.app.features.dialog_show_details_champ.DialogShowDetailsChamp
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.activity_details_champ.*
import org.koin.android.viewmodel.ext.android.viewModel


class DetailsChampActivity : AppCompatActivity(),
    OnItemClickListener {
    private val detailsViewModel: DetailsViewModel by viewModel()
    private var adapterShowDetailsChamp: AdapterShowDetailsChamp? = null
    private var champId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_champ)
        champId =
            getChamp(
                intent
            )
        observerViewModel()
        setupUi()
        if (champId != null) {
            detailsViewModel.getChampById(champId!!)
        }
    }

    private fun observerViewModel() {
        detailsViewModel.getChampDetailsLiveData().observe(this, Observer {
            toolbar_title.text = it.headerModel?.name.defaultEmpty()
            champ_cost.text = it.headerModel?.cost.defaultEmpty()
            adapterShowDetailsChamp?.addData(it)
            Log.d("Phuc", "$it")
        })
    }

    private fun setupUi() {
        rv_show_details_champ?.layoutManager = LinearLayoutManager(this)
        adapterShowDetailsChamp =
            AdapterShowDetailsChamp(this)
        rv_show_details_champ?.adapter = adapterShowDetailsChamp

        item_btn_back?.setOnClickListener {
            finish()
        }
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
        val dialog = DialogShowDetailsChamp.newInstance(id)
        dialog.show(supportFragmentManager, "DialogShowDetailsChamp")
    }
}


