package com.tft_mvvm.app.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.tft_mvvm.champ.R
import com.example.presentation.features.champs.model.model.Champ
import com.example.presentation.features.champs.model.viewmodel.DetailsViewModel
import com.tft_mvvm.app.adapter.MyAdapter
import com.tft_mvvm.app.ui.OnItemClickListener
import kotlinx.android.synthetic.main.activity_details_champ.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsChamp : AppCompatActivity(),
    OnItemClickListener {
    private var adapterShowChampsByOrigin: MyAdapter? = null
    private var adapterShowChampByClasss: MyAdapter? = null
    private val detailsViewModel: DetailsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_champ)
        val intent = intent
        val champ: Champ = intent.getSerializableExtra("champ") as Champ
        getChampsByOrigin(champ)
        getChampsByClass(champ)
        setupUI(champ)
    }
    private fun setupUI(champ:Champ) {
        toolbar_title.text = champ.name
        origin.text=champ.origin
        classs.text=champ.classs
        Glide.with(this).load(champ.linkSkilAvatar).into(skill_avatar)
        Glide.with(this).load(champ.linkChampCover).into(champ_cover)
        skill_name.text=champ.skillName
        activated.text=champ.activated
        rv_origin.layoutManager = GridLayoutManager(this,4)
        adapterShowChampsByOrigin = MyAdapter(arrayListOf(),this)
        rv_origin.adapter=adapterShowChampsByOrigin
        rv_classs.layoutManager =GridLayoutManager(this,4)
        adapterShowChampByClasss = MyAdapter(arrayListOf(),this)
        rv_classs.adapter = adapterShowChampByClasss
    }

    private fun getChampsByOrigin(champ: Champ) {

        detailsViewModel.getChampsByOrigin(champ.origin)
        detailsViewModel.getchampByOriginLiveData()
            .observe(this, Observer {
                it.sortedBy { champ -> champ.coat }
                adapterShowChampsByOrigin?.addData(it)
            })
    }

    private fun getChampsByClass(champ: Champ) {

        detailsViewModel.getChampsByClass(champ.classs)
        detailsViewModel.getchampByClassLiveData()
            .observe(this, Observer {
                it.sortedBy { champ -> champ.coat }
                adapterShowChampByClasss?.addData(it)
            })
    }

    override fun onClickListener(champ: Champ) {
        Intent(
            this,
            DetailsChamp::class.java
        ).also {
            it.putExtra("champ", champ)
            startActivity(it)
        }
    }
}


