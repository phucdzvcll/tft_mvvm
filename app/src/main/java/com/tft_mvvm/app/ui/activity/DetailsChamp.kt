package com.tft_mvvm.app.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.tft_mvvm.champ.R
import com.example.presentation.features.champs.model.model.Champ
//import com.example.presentation.features.champs.model.model.Skill
import com.example.presentation.features.champs.model.viewmodel.MainViewModel
import com.tft_mvvm.app.adapter.MyAdapter
import com.tft_mvvm.app.ui.OnItemClickListener
import kotlinx.android.synthetic.main.activity_details_champ.*
import kotlinx.android.synthetic.main.item.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsChamp : AppCompatActivity(),
    OnItemClickListener {
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var champsByOrigin: ArrayList<Champ>
    private lateinit var champsByClasss: ArrayList<Champ>
    private lateinit var adapterShowChampsByOrigin: MyAdapter
    private lateinit var adapterShowChampByClasss: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_champ)
        val intent = intent
        val champ: Champ = intent.getSerializableExtra("champ") as Champ
        getChampsByOrigin(champ)
        getChampsByClasss(champ)
        setupUI(champ)
    }
    private fun setupUI(champ:Champ) {
        toolbar_title.text = champ.name
       origin.text=champ.origin
       classs.text=champ.classs
        Glide.with(this).load(champ.linkSkilAvatar).into(skill_avatar);
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
        champsByOrigin = ArrayList(arrayListOf())
        mainViewModel.getChampByOrigin(
            name = "",
            linkImg = "",
            coat = "",
            origin = champ.origin,
            classs = "",
            id = "",
            linkSkillAvatar = "",
            activated = "",
            skillName = ""
        )
        mainViewModel.getChampsByOriginLiveData()
            .observe(this, Observer {
                champsByOrigin.clear()
                champsByOrigin.addAll(it)
                adapterShowChampsByOrigin.addData(champsByOrigin)
            })
    }
    private fun getChampsByClasss(champ: Champ) {
        champsByClasss = ArrayList(arrayListOf())
        mainViewModel.getChampByClasss(
            name = "",
            linkImg = "",
            coat = "",
            origin = "",
            classs = champ.classs,
            id = "",
            linkSkillAvatar = "",
            activated = "",
            skillName = ""
        )
        mainViewModel.getChampsByClassLiveData()
            .observe(this, Observer {
                champsByClasss.clear()
                champsByClasss.addAll(it)
                adapterShowChampByClasss.addData(champsByClasss)
            })
    }

    override fun onClickListener(champ: Champ) {
        val intent: Intent = Intent(
            this,
            DetailsChamp::class.java
        )
        intent.putExtra("champ", champ)
        startActivity(intent)
    }
}


