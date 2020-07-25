package com.tft_mvvm.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.presentation.features.champs.model.model.Champ
import com.example.presentation.features.champs.model.viewmodel.ChampViewModel
import com.tft_mvvm.app.adapter.MyAdapter
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(),OnItemClickListener {
    private val champViewModel: ChampViewModel by viewModel()
    private lateinit var adapter: MyAdapter
    private lateinit var champs: ArrayList<Champ>
    private var isLoading: Boolean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        getChamps()
        sort()
    }

    private fun setupUI() {
        adapter = MyAdapter(arrayListOf())
        rv.layoutManager = GridLayoutManager(this, 5)
        rv.adapter = adapter
        swipe_refresh_layout.setOnRefreshListener {
            getChamps()
            group_radio_btn.clearCheck()
            swipe_refresh_layout.isRefreshing = isLoading!!
        }
    }

    private fun sort() {
        btn_sort_by_name.setOnClickListener {
            if(isLoading==false){
                adapter.sortByName()
            }
        }
        btn_sort_by_coat.setOnClickListener {
            if(isLoading==false){
                adapter.sortByCoat()
            }
        }
    }

    private fun getChamps() {
        champs = ArrayList(arrayListOf())
        champViewModel.getChamps(name = "", linkImg = "", coat = "")
        champViewModel.getChampsLiveData()
            .observe(this, Observer {
                champs.clear()
                champs.addAll(it)
                adapter.addData(champs)
            })
        champViewModel.isRefresh()
            .observe(this, Observer { isLoading = it })
    }

    override fun onClickListener(champ: Champ?) {

    }
}