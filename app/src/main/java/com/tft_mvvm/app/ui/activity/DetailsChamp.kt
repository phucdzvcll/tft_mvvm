package com.tft_mvvm.app.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.tft_mvvm.champ.R
import com.tft_mvvm.app.features.champ.model.Champ
//import com.example.presentation.features.champs.model.model.Skill
import com.tft_mvvm.app.features.champ.viewmodel.MainViewModel
import com.tft_mvvm.app.ui.adapter.MyAdapter
import com.tft_mvvm.app.ui.OnItemClickListener
import kotlinx.android.synthetic.main.activity_details_champ.*
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
        setupUI(champ)
    }
    private fun setupUI(champ: Champ) {
        toolbar_title.text = champ.name
       origin.text=champ.origin
       classs.text=champ.classs
        Glide.with(this).load(champ.linkSkilAvatar).into(skill_avatar);
        Glide.with(this).load(champ.linkChampCover).into(champ_cover);
        skill_name.text=champ.skillName
        activated.text=champ.activated
       rv_origin.layoutManager = GridLayoutManager(this,4)
       adapterShowChampsByOrigin = MyAdapter(arrayListOf(),this)
        rv_origin.adapter=adapterShowChampsByOrigin
        rv_classs.layoutManager =GridLayoutManager(this,4)
        adapterShowChampByClasss = MyAdapter(arrayListOf(),this)
        rv_classs.adapter = adapterShowChampByClasss
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


